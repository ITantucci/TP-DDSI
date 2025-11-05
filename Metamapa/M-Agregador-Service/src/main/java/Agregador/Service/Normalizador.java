package Agregador.Service;
import Agregador.business.Hechos.Hecho;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;
/**
 * Normaliza y unifica hechos antes de persistir:
 * - Mapea categorías a un canon (sinónimos -> canonical)
 * - Limpia texto (minúsculas, sin tildes, espacios)
 * - Redondea lat/lon a N decimales
 * - Deduplica por (titulo_norm, fechaHecho, lat_norm, lon_norm) y MERGEA campos
 */
@Component
public class Normalizador {
  private final Map<String, String> categoriasCanon; // clave: variante normalizada, valor: categoría canónica
  private final int decimalesCoordenadas;

  public Normalizador(@Value("${normalizador.decimales:4}") int decimalesCoordenadas) {
    this.categoriasCanon = defaultDict(); // o cargar por otro lado
    this.decimalesCoordenadas = decimalesCoordenadas;
  }

  /** === API principal === */
  public List<Hecho> normalizarYUnificar(List<Hecho> hechos) {
    if (hechos == null || hechos.isEmpty()) return List.of();
    // 1) normalizar campos
    List<Hecho> normalizados = hechos.stream()
            .map(this::normalizarHecho)
            .toList();
    // 2) deduplicar + merge según clave compuesta
    Map<String, Hecho> porClave = new LinkedHashMap<>();
    for (Hecho h : normalizados) {
      String key = clave(h);
      porClave.merge(key, h, this::mergeHechos);
    }
    return new ArrayList<>(porClave.values());
  }

  /** Normaliza un hecho in-place y lo devuelve */
  public Hecho normalizarHecho(Hecho h) {
    if (h == null) return null;
    // Título / Descripción: limpiar
    if (h.getTitulo() != null)        h.setTitulo(limpiarEspacios(h.getTitulo()).trim());
    if (h.getDescripcion() != null)   h.setDescripcion(limpiarEspacios(h.getDescripcion()).trim());
    // Categoría: normalizar con diccionario
    h.setCategoria(normalizarCategoria(h.getCategoria()));
    // Lat/Lon: redondeo
    if (h.getLatitud() != null)  h.setLatitud(redondear(h.getLatitud(), decimalesCoordenadas));
    if (h.getLongitud() != null) h.setLongitud(redondear(h.getLongitud(), decimalesCoordenadas));
    // Fechas: asegurar consistencia básica (si viene nula, no forzar)
    if (h.getFechaCarga() == null)        h.setFechaCarga(LocalDateTime.now());
    if (h.getFechaModificacion() == null) h.setFechaModificacion(LocalDateTime.now());
    // Metadata: normalizar claves a lower (opcional)
    if (h.getMetadata() != null && !h.getMetadata().isEmpty()) {
      HashMap<String, String> nueva = new HashMap<>();
      h.getMetadata().forEach((k, v) -> {
        if (k != null) nueva.put(k.trim().toLowerCase(Locale.ROOT), v);
      });
      h.setMetadata(nueva);
    }
    return h;
  }

  /** === Categorías === */
  public String normalizarCategoria(String categoriaCruda) {
    if (categoriaCruda == null || categoriaCruda.isBlank()) return "Desconocido";
    String clave = toComparable(categoriaCruda);

    // 1️⃣ detectar palabra clave si la frase es larga
    // patrón simple, podés ampliar esta lista según tu dominio
    Map<String, String> patrones = Map.<String, String>ofEntries(
            // --- Incendios ---
            Map.entry("incendio", "Incendio"),
            Map.entry("fuego", "Incendio"),
            Map.entry("quema", "Incendio"),
            Map.entry("llamas", "Incendio"),
            Map.entry("explosion seguida de incendio", "Incendio"),
            // --- Explosiones ---
            Map.entry("explosion", "Explosión"),
            Map.entry("detonacion", "Explosión"),
            Map.entry("deflagracion", "Explosión"),
            // --- Gas / Sustancias ---
            Map.entry("gas", "Fuga o emanación de gas"),
            Map.entry("escape de gas", "Fuga o emanación de gas"),
            Map.entry("fuga", "Fuga o emanación de gas"),
            Map.entry("emanacion", "Fuga o emanación de gas"),
            Map.entry("perdida de gas", "Fuga o emanación de gas"),
            Map.entry("derrame quimico", "Accidente químico"),
            Map.entry("accidente quimico", "Accidente químico"),
            Map.entry("emanacion toxica", "Accidente químico"),
            // --- Transporte ---
            Map.entry("descarrilamiento", "Accidente ferroviario"),
            Map.entry("avion", "Accidente aéreo"),
            Map.entry("aeronave", "Accidente aéreo"),
            Map.entry("transporte publico", "Accidente de transporte"),
            Map.entry("paso a nivel", "Accidente de transporte"),
            Map.entry("choque en cadena", "Siniestro vial"),
            Map.entry("colision", "Siniestro vial"),
            Map.entry("volcamiento", "Siniestro vial"),
            Map.entry("atropello", "Siniestro vial"),
            // --- Industrial ---
            Map.entry("maquinaria industrial", "Accidente industrial"),
            Map.entry("planta procesadora", "Accidente industrial"),
            Map.entry("fabrica", "Accidente industrial"),
            Map.entry("explosion industrial", "Accidente industrial"),
            Map.entry("fallo en sistema de seguridad industrial", "Accidente industrial"),
            // --- Derrumbes / Deslizamientos ---
            Map.entry("derrumbe", "Deslizamiento o derrumbe"),
            Map.entry("aluvion", "Deslizamiento o derrumbe"),
            Map.entry("deslizamiento", "Deslizamiento o derrumbe"),
            // --- Meteorológicos ---
            Map.entry("viento", "Viento fuerte"),
            Map.entry("rafaga", "Viento fuerte"),
            Map.entry("huracan", "Viento fuerte"),
            Map.entry("tormenta", "Tormenta"),
            Map.entry("granizo", "Granizo"),
            Map.entry("lluvia", "Lluvia"),
            Map.entry("precipitacion", "Lluvia"),
            Map.entry("inundacion", "Inundación"),
            Map.entry("anegamiento", "Inundación"),
            Map.entry("torrente", "Inundación"),
            // --- Energía / Infraestructura ---
            Map.entry("apagon", "Corte de energía"),
            Map.entry("corte de luz", "Corte de energía"),
            Map.entry("fallo electrico", "Fallo de infraestructura"),
            Map.entry("red de distribucion", "Fallo de infraestructura"),
            Map.entry("sistema de seguridad", "Fallo de infraestructura"),
            // --- Salud / Sanitario ---
            Map.entry("epidemia", "Emergencia sanitaria"),
            Map.entry("virus", "Emergencia sanitaria"),
            Map.entry("infeccion", "Emergencia sanitaria"),
            Map.entry("intoxicacion", "Emergencia sanitaria"),
            // --- Otros / Contaminación ---
            Map.entry("contaminacion", "Contaminación"),
            Map.entry("planta industrial", "Accidente industrial"),
            Map.entry("contaminación ambiental", "Contaminación"),
            Map.entry("vertido contaminante", "Contaminación"),
            Map.entry("polución industrial", "Contaminación"),
            Map.entry("crisis ambiental por contaminantes", "Contaminación"),
            Map.entry("derrame en curso de agua", "Contaminación"),
            Map.entry("impacto de contaminantes", "Contaminación"),
            // Emergencia sanitaria
            Map.entry("crisis sanitaria", "Emergencia sanitaria"),
            Map.entry("propagación de enfermedad", "Emergencia sanitaria"),
            Map.entry("brote epidémico", "Emergencia sanitaria"),
            Map.entry("emergencia de salud pública", "Emergencia sanitaria"),
            Map.entry("casos agrupados de enfermedad", "Emergencia sanitaria"),
            Map.entry("brote de enfermedad contagiosa", "Emergencia sanitaria"),
            // Derrames / Fugas
            Map.entry("fuga de material peligroso", "Derrame / Fuga de sustancias"),
            Map.entry("vertido de químicos", "Derrame / Fuga de sustancias"),
            Map.entry("derrame", "Derrame / Fuga de sustancias"),
            Map.entry("derrames de sustancias químicas", "Derrame / Fuga de sustancias"),
            // Intoxicación masiva
            Map.entry("intoxicación alimentaria masiva", "Intoxicación masiva"),
            Map.entry("intoxicación por consumo", "Intoxicación masiva"),
            Map.entry("intoxicación por sustancias químicas", "Intoxicación masiva"),
            Map.entry("casos múltiples de intoxicación", "Intoxicación masiva"),
            // --- Material volcánico ---
            Map.entry("precipitación de material volcánico", "Material volcánico"),
            Map.entry("emisión volcánica", "Material volcánico"),
            Map.entry("polvo volcánico en suspensión", "Material volcánico"),
            // --- Sequía / Escasez de agua ---
            Map.entry("sequía extrema", "Sequía"),
            Map.entry("sequía con pérdidas agrícolas", "Sequía"),
            Map.entry("escasez de agua", "Escasez de agua"),
            // --- Tormentas y fenómenos meteorológicos ---
            Map.entry("tormenta con piedras de granizo", "Tormenta / Granizo"),
            Map.entry("tormenta de granizo", "Tormenta / Granizo"),
            Map.entry("tormenta de nieve", "Tormenta de nieve"),
            Map.entry("vendaval", "Viento fuerte"),
            Map.entry("temporal de viento", "Viento fuerte"),
            Map.entry("vientos huracanados", "Viento huracanado"),
            Map.entry("vientos con fuerza ciclónica", "Viento huracanado"),
            // --- Inundaciones / Anegamiento ---
            Map.entry("anegamiento masivo", "Inundación"),
            Map.entry("desborde de río", "Inundación"),
            Map.entry("desborde de arroyo", "Inundación"),
            Map.entry("inundación por lluvias intensas", "Inundación"),
            // --- Temperaturas extremas ---
            Map.entry("frío extremo", "Temperatura extrema"),
            Map.entry("ola de calor extremo", "Temperatura extrema"),
            Map.entry("emergencia por altas temperaturas", "Temperatura extrema"),
            // --- Sismos / Terremotos ---
            Map.entry("sismo de gran magnitud", "Sismo / Terremoto"),
            Map.entry("terremoto destructivo", "Sismo / Terremoto"),
            // --- Fuego forestal ---
            Map.entry("fuego en bosque nativo", "Incendio forestal"),
            Map.entry("incendio forestal", "Incendio forestal"),
            Map.entry("incendio en zona de monte", "Incendio forestal")
    );

    for (String palabra : patrones.keySet()) {
      if (clave.contains(palabra))
        return patrones.get(palabra);
    }

    // 2️⃣ intento exacto en el diccionario principal
    if (categoriasCanon.containsKey(clave))
      return categoriasCanon.get(clave);

    // 3️⃣ fuzzy matching como respaldo
    JaroWinklerSimilarity similarity = new JaroWinklerSimilarity();
    String mejor = "Desconocido";
    double mejorScore = 0.7;

    for (String canonKey : categoriasCanon.keySet()) {
      double score = similarity.apply(clave, canonKey);
      if (score > mejorScore) {
        mejorScore = score;
        mejor = categoriasCanon.get(canonKey);
      }
    }
    if (mejor.equals("Desconocido")) {
      String claveNorm = java.text.Normalizer
              .normalize(categoriaCruda, java.text.Normalizer.Form.NFD)
              .replaceAll("\\p{M}", "");
      String[] palabras = claveNorm.split("\\s+");
      String palabraLarga1 = null;
      String palabraLarga2 = null;
      for (String palabra : palabras) {
        if (palabraLarga1 == null || palabra.length() > palabraLarga1.length()) {
          palabraLarga2 = palabraLarga1;
          palabraLarga1 = palabra;
        } else if (palabraLarga2 == null || palabra.length() > palabraLarga2.length()) {
          palabraLarga2 = palabra;
        }
      }
      if (palabraLarga1 != null && palabraLarga2 != null) {
        return palabraLarga1 + " " + palabraLarga2;
      } else if (palabraLarga1 != null) {
        return palabraLarga1;
      } else {
        return "Desconocido";
      }
    }
    return mejor;
  }

  /** === Dedupe key === */
  private String clave(Hecho h) {
    String tituloCmp = toComparable(Optional.ofNullable(h.getTitulo()).orElse(""));
    String fecha     = Optional.ofNullable(h.getFechaHecho()).map(LocalDateTime::toString).orElse("0000-00-00");
    // Construimos el formato correctamente y de forma segura
    String formatCoords = String.format("%%.%df", decimalesCoordenadas);
    String lat = h.getLatitud()  == null ? "null" : String.format(Locale.ROOT, formatCoords, h.getLatitud());
    String lon = h.getLongitud() == null ? "null" : String.format(Locale.ROOT, formatCoords, h.getLongitud());
    return String.join("|", tituloCmp, fecha, lat, lon);
  }

  /** === Merge de dos hechos duplicados === */
  private Hecho mergeHechos(Hecho base, Hecho nuevo) {
    // Preferir valores NO nulos; combinar colecciones; conservar fechas coherentes
    if (isNullOrBlank(base.getDescripcion()) && !isNullOrBlank(nuevo.getDescripcion())) base.setDescripcion(nuevo.getDescripcion());
    if ("desconocido".equalsIgnoreCase(Optional.ofNullable(base.getCategoria()).orElse("desconocido"))
            && !isNullOrBlank(nuevo.getCategoria()))
      base.setCategoria(nuevo.getCategoria());
    if (base.getLatitud() == null && nuevo.getLatitud() != null)   base.setLatitud(nuevo.getLatitud());
    if (base.getLongitud() == null && nuevo.getLongitud() != null) base.setLongitud(nuevo.getLongitud());
    if (base.getFechaHecho() == null && nuevo.getFechaHecho() != null) base.setFechaHecho(nuevo.getFechaHecho());
    // anonimo / eliminado: si alguno marca true, preservar true (conservador)
    base.setAnonimo(Boolean.TRUE.equals(base.getAnonimo()) || Boolean.TRUE.equals(nuevo.getAnonimo()));
    base.setEliminado(Boolean.TRUE.equals(base.getEliminado()) || Boolean.TRUE.equals(nuevo.getEliminado()));
    // fechas de auditoría
    if (nuevo.getFechaCarga() != null && (base.getFechaCarga() == null || nuevo.getFechaCarga().isBefore(base.getFechaCarga())))
      base.setFechaCarga(nuevo.getFechaCarga());
    if (nuevo.getFechaModificacion() != null && (base.getFechaModificacion() == null || nuevo.getFechaModificacion().isAfter(base.getFechaModificacion())))
      base.setFechaModificacion(nuevo.getFechaModificacion());
    // multimedia: merge por (tipo+path) evitando duplicados
    if (nuevo.getMultimedia() != null && !nuevo.getMultimedia().isEmpty()) {
      if (base.getMultimedia() == null) base.setMultimedia(new ArrayList<>());
      var existentes = base.getMultimedia();
      var firmas = existentes.stream()
              .map(m -> (m.getTipoMultimedia() + "|" + m.getPath()))
              .collect(Collectors.toSet());
      nuevo.getMultimedia().forEach(m -> {
        String f = m.getTipoMultimedia() + "|" + m.getPath();
        if (!firmas.contains(f)) {
          existentes.add(m);
          firmas.add(f);
        }
      });
    }
    // metadata: completar claves faltantes, no sobreescribir existentes
    if (nuevo.getMetadata() != null && !nuevo.getMetadata().isEmpty()) {
      if (base.getMetadata() == null) base.setMetadata(new HashMap<>());
      nuevo.getMetadata().forEach((k, v) -> base.getMetadata().putIfAbsent(k, v));
    }
    return base;
  }

  /** === Helpers === */
  private String toComparable(String s) {
    if (s == null) return "";
    String t = s.trim().toLowerCase(Locale.ROOT);
    // quitar tildes
    t = Normalizer.normalize(t, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
    // colapsar espacios
    return t.replaceAll("\\s+", " ");
  }

  private String limpiarEspacios(String s) {
    if (s == null) return null;
    return s.replaceAll("\\s+", " ");
  }

  private boolean isNullOrBlank(String s) {
    return s == null || s.isBlank();
  }

  private Float redondear(Float valor, int decimales) {
    if (valor == null) return null;
    double factor = Math.pow(10, decimales);
    return (float) (Math.round(valor * factor) / factor);
  }

  /** Diccionario por defecto (opcional) */
  // --- defaultNormalizer debe llamar al ctor (int) ---
  public static Normalizador defaultNormalizer() {
    return new Normalizador(4);
  }
  private static Map<String, String> defaultDict() {
    Map<String, String> dict = new HashMap<>();
    // --- Incendios ---
    dict.put("incendio", "Incendio");
    dict.put("incendios", "Incendio");
    dict.put("fuego", "Incendio");
    dict.put("llamas", "Incendio");
    dict.put("quemazon", "Incendio");
    dict.put("explosion seguida de incendio", "Incendio");
    dict.put("incendio forestal", "Incendio forestal");
    dict.put("fuego forestal", "Incendio forestal");
    dict.put("incendio en zona de monte", "Incendio forestal");
    // --- Explosiones ---
    dict.put("explosion", "Explosión");
    dict.put("detonacion", "Explosión");
    dict.put("deflagracion", "Explosión");
    dict.put("explosion industrial", "Accidente industrial");
    // --- Gas / Sustancias ---
    dict.put("gas", "Fuga o emanación de gas");
    dict.put("escape de gas", "Fuga o emanación de gas");
    dict.put("fuga", "Fuga o emanación de gas");
    dict.put("emanacion", "Fuga o emanación de gas");
    dict.put("perdida de gas", "Fuga o emanación de gas");
    dict.put("derrame quimico", "Accidente químico");
    dict.put("accidente quimico", "Accidente químico");
    dict.put("emanacion toxica", "Accidente químico");
    dict.put("fuga de material peligroso", "Derrame / Fuga de sustancias");
    dict.put("vertido de quimicos", "Derrame / Fuga de sustancias");
    dict.put("derrame", "Derrame / Fuga de sustancias");
    dict.put("derrames de sustancias quimicas", "Derrame / Fuga de sustancias");
    // --- Transporte ---
    dict.put("descarrilamiento", "Accidente ferroviario");
    dict.put("avion", "Accidente aéreo");
    dict.put("aeronave", "Accidente aéreo");
    dict.put("transporte publico", "Accidente de transporte");
    dict.put("paso a nivel", "Accidente de transporte");
    dict.put("choque en cadena", "Siniestro vial");
    dict.put("colision", "Siniestro vial");
    dict.put("volcamiento", "Siniestro vial");
    dict.put("atropello", "Siniestro vial");
    // --- Industrial ---
    dict.put("maquinaria industrial", "Accidente industrial");
    dict.put("planta procesadora", "Accidente industrial");
    dict.put("fabrica", "Accidente industrial");
    dict.put("fallo en sistema de seguridad industrial", "Accidente industrial");
    dict.put("planta industrial", "Accidente industrial");
    // --- Meteorológicos ---
    dict.put("viento", "Viento fuerte");
    dict.put("rafaga", "Viento fuerte");
    dict.put("huracan", "Viento fuerte");
    dict.put("vendaval", "Viento fuerte");
    dict.put("temporal de viento", "Viento fuerte");
    dict.put("vientos huracanados", "Viento huracanado");
    dict.put("vientos con fuerza ciclonica", "Viento huracanado");
    dict.put("tormenta", "Tormenta");
    dict.put("granizo", "Granizo");
    dict.put("lluvia", "Lluvia");
    dict.put("precipitacion", "Lluvia");
    dict.put("tormenta con piedras de granizo", "Tormenta / Granizo");
    dict.put("tormenta de granizo", "Tormenta / Granizo");
    dict.put("tormenta de nieve", "Tormenta de nieve");
    // --- Inundaciones / Anegamiento ---
    dict.put("inundacion", "Inundación");
    dict.put("anegamiento", "Inundación");
    dict.put("torrente", "Inundación");
    dict.put("anegamiento masivo", "Inundación");
    dict.put("desborde de rio", "Inundación");
    dict.put("desborde de arroyo", "Inundación");
    dict.put("inundacion por lluvias intensas", "Inundación");
    // --- Salud / Emergencia sanitaria ---
    dict.put("epidemia", "Emergencia sanitaria");
    dict.put("virus", "Emergencia sanitaria");
    dict.put("infeccion", "Emergencia sanitaria");
    dict.put("intoxicacion", "Emergencia sanitaria");
    dict.put("crisis sanitaria", "Emergencia sanitaria");
    dict.put("propagacion de enfermedad", "Emergencia sanitaria");
    dict.put("brote epidemico", "Emergencia sanitaria");
    dict.put("emergencia de salud publica", "Emergencia sanitaria");
    dict.put("casos agrupados de enfermedad", "Emergencia sanitaria");
    dict.put("brote de enfermedad contagiosa", "Emergencia sanitaria");
    dict.put("intoxicacion alimentaria masiva", "Intoxicacion masiva");
    dict.put("intoxicacion por consumo", "Intoxicacion masiva");
    dict.put("intoxicacion por sustancias quimicas", "Intoxicacion masiva");
    dict.put("casos multiples de intoxicacion", "Intoxicacion masiva");
    // --- Otros / Contaminación ---
    dict.put("contaminacion", "Contaminacion");
    dict.put("contaminacion ambiental", "Contaminacion");
    dict.put("vertido contaminante", "Contaminacion");
    dict.put("polucion industrial", "Contaminacion");
    dict.put("crisis ambiental por contaminantes", "Contaminacion");
    dict.put("derrame en curso de agua", "Contaminacion");
    dict.put("impacto de contaminantes", "Contaminacion");
    // --- Sequía / Escasez de agua ---
    dict.put("sequia extrema", "Sequia");
    dict.put("sequia con perdidas agricola", "Sequia");
    dict.put("escasez de agua", "Escasez de agua");
    // --- Material volcánico ---
    dict.put("precipitacion de material volcanico", "Material volcanico");
    dict.put("emision volcanica", "Material volcanico");
    dict.put("polvo volcanico en suspension", "Material volcanico");
    // --- Temperaturas extremas ---
    dict.put("frio extremo", "Temperatura extrema");
    dict.put("ola de calor extremo", "Temperatura extrema");
    dict.put("emergencia por altas temperaturas", "Temperatura extrema");
    // --- Delitos / Protestas ---
    dict.put("protesta", "Protesta");
    dict.put("manifestacion", "Protesta");
    dict.put("marcha", "Protesta");
    dict.put("delito", "Delito");
    // Normalizar claves (minúsculas, sin tildes)
    Map<String, String> normalizado = new HashMap<>();
    dict.forEach((k, v) -> {
      String key = java.text.Normalizer.normalize(k.toLowerCase(Locale.ROOT), java.text.Normalizer.Form.NFD)
              .replaceAll("\\p{M}", "");
      normalizado.put(key, v);
    });
    return normalizado;
  }
}