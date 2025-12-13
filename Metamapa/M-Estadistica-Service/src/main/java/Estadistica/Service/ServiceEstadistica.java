package Estadistica.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import Estadistica.persistencia.*;
import Estadistica.business.Colecciones.Coleccion;
import Estadistica.business.Estadistica.*;

@Service
public class ServiceEstadistica {
    private final String baseUrl;
    private final GeocodingService geocodingService;
    private final RepositorioHechos repositorioHechos;
    private final RepositorioSolicitudesEliminacion repositorioSolicitudesEliminacion;
    private final RepositorioColecciones repositorioColecciones;
    private final ServiceAgregador serviceAgregador;
    private final RepositorioEstadisticas repositorioEstadisticas;

    public ServiceEstadistica(RestTemplate restTemplate,
                              @Value("${M.Agregador.Service.url}") String baseUrl, GeocodingService geocodingService,
                              RepositorioHechos repositorioHechos,
                              RepositorioSolicitudesEliminacion repositorioSolicitudesEliminacion,
                              RepositorioColecciones repositorioColecciones, ServiceAgregador serviceAgregador,
                              RepositorioEstadisticas repositorioEstadisticas) {
        this.baseUrl = baseUrl;
        this.geocodingService = geocodingService;
        this.repositorioHechos = repositorioHechos;
        this.repositorioSolicitudesEliminacion = repositorioSolicitudesEliminacion;
        this.repositorioColecciones = repositorioColecciones;
        this.serviceAgregador = serviceAgregador;
        this.repositorioEstadisticas = repositorioEstadisticas;
    }

    public void actualizar() {
        CantidadDeSpam spamStats = estadisticaSpam();

        CategoriaConMasHechos topCategoriaStats = estadisticaCategoriaMasReportada();

        repositorioEstadisticas.save(spamStats);
        repositorioEstadisticas.save(topCategoriaStats);

        List<String> categorias = repositorioHechos.obtenerCategorias();
        for (String categoria : categorias) {
        HoraConMasHechosPorCategoria horaStats = estadisticaHoraCategoria(categoria);
        ProvinciaConMasHechosPorCategoria provinciaCategoriaStats = estadisticaProvinciaCategoria(categoria);

        repositorioEstadisticas.save(horaStats);
        repositorioEstadisticas.save(provinciaCategoriaStats);
        }
        List <Coleccion> colecciones = repositorioColecciones.findAll();
        for (Coleccion coleccion : colecciones) {
            ProvinciaConMasHechosPorColeccion coleccionStats = estadisticaColeccionProvincia(coleccion.getHandle());
            repositorioEstadisticas.save(coleccionStats);
        }
    }

/*    public void actualizarDashboards() {


    }*/

    public String exportarCsv() {
        StringBuilder csv = new StringBuilder();
        CategoriaConMasHechos topCategoriaStats = estadisticaCategoriaMasReportada();
        String categoriaBase = topCategoriaStats.getCategoria();
        // CONDICIÓN DE NO EXPORTAR: Si la categoría base es N/A, no hay datos válidos para filtrar.
        if ("N/A".equals(categoriaBase) || categoriaBase.isBlank()) {
            return ""; // Devolvemos String vacío para que el Controller retorne 204 No Content
        }
        // UUID de ejemplo (Asumimos que el Administrador definiría esto, pero para la exportación genérica, usamos un placeholder)
        UUID coleccionEjemplo = UUID.fromString("00000000-0000-0000-0000-000000000001");
        CantidadDeSpam spamStats = estadisticaSpam();
        HoraConMasHechosPorCategoria horaStats = estadisticaHoraCategoria(categoriaBase);
        ProvinciaConMasHechosPorCategoria provinciaCategoriaStats = estadisticaProvinciaCategoria(categoriaBase);
        ProvinciaConMasHechosPorColeccion coleccionStats = estadisticaColeccionProvincia(coleccionEjemplo);
        // Formato Analítico Simple: Tipo_de_Estadistica,Clave,Valor
        csv.append("Tipo_de_Estadistica,Clave,Valor\n");
        // I. ¿Cuántas solicitudes de eliminación son spam?
        csv.append("RESUMEN_SPAM,Solicitudes_Spam,")
                .append(spamStats.getCantidadSolicitudesSpam())
                .append("\n");
        // II. ¿Cuál es la categoría con mayor cantidad de hechos reportados?
        csv.append("CATEGORIA_MAS_REPORTADA,Categoria_Ganadora,")
                .append(topCategoriaStats.getCategoria()).append("\n");
        // III. ¿A qué hora del día ocurren la mayor cantidad de hechos de una cierta categoría?
        csv.append("HORA_PICO_POR_CATEGORIA,Categoria_Base,")
                .append(categoriaBase).append("\n");
        csv.append("HORA_PICO_POR_CATEGORIA,Hora_Mas_Frecuente,")
                .append(horaStats.getHora() == null ? "N/A" : horaStats.getHora());
        // IV. ¿En qué provincia se presenta la mayor cantidad de hechos de una cierta categoría?
        csv.append("PROVINCIA_PICO_POR_CATEGORIA,Categoria_Base,")
                .append(categoriaBase)
                .append("\n");
        csv.append("PROVINCIA_PICO_POR_CATEGORIA,Provincia_Mas_Frecuente,")
                .append(provinciaCategoriaStats.getProvincia() == null ? "N/A" : provinciaCategoriaStats.getProvincia())
                .append("\n");
        // V. De una colección, ¿en qué provincia se agrupan la mayor cantidad de hechos reportados?
        csv.append("PROVINCIA_PICO_POR_COLECCION,ID_Coleccion_Analizada,")
                .append(coleccionEjemplo).append("\n");
        csv.append("PROVINCIA_PICO_POR_COLECCION,Provincia_Mas_Frecuente,")
                .append(coleccionStats.getProvincia()).append("\n");

        repositorioEstadisticas.save(spamStats);
        repositorioEstadisticas.save(horaStats);
        repositorioEstadisticas.save(provinciaCategoriaStats);
        repositorioEstadisticas.save(coleccionStats);
        repositorioEstadisticas.save(topCategoriaStats);

        return csv.toString();
    }

    //¿Cuántas solicitudes de eliminación son spam?
    public CantidadDeSpam estadisticaSpam() {
        long spam = repositorioSolicitudesEliminacion.countByEstado("spam");
        return new CantidadDeSpam(spam);
    }

    //¿A qué hora del día ocurren la mayor cantidad de hechos de una cierta categoría?
    public HoraConMasHechosPorCategoria estadisticaHoraCategoria(String categoria) {
        String hora = repositorioHechos.obtenerHoraConMasHechos(categoria).orElse("N/A");
        return new HoraConMasHechosPorCategoria(hora,categoria);
    }

    public String getHora(LocalDateTime fechayhora) {
        return fechayhora.format(DateTimeFormatter.ofPattern("HH"));
    }

    //¿En qué provincia se presenta la mayor cantidad de hechos de una cierta categoría?
    public ProvinciaConMasHechosPorCategoria estadisticaProvinciaCategoria(String categoria) {
        Map<String, String> estadisticaProvinciaCategoria = new HashMap<>();
        String provincia = repositorioHechos.findByCategoriaIgnoreCase(categoria)
                .stream().filter(h -> getProvincia(h.getLatitud(), h.getLongitud()) != null)
                .collect(Collectors.groupingBy(h -> getProvincia(h.getLatitud(), h.getLongitud()), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");
        estadisticaProvinciaCategoria.put("categoria", categoria);
        estadisticaProvinciaCategoria.put("provincia", provincia);
        return new ProvinciaConMasHechosPorCategoria(provincia,categoria);
    }

    //De una colección, ¿en qué provincia se agrupan la mayor cantidad de hechos reportados?
    public ProvinciaConMasHechosPorColeccion estadisticaColeccionProvincia(UUID idColeccion) {
        Coleccion coleccion = repositorioColecciones.findById(idColeccion).orElse(null);
        String provincia = repositorioHechos.filtrarPorCriterios(coleccion.getCriterios())
                .stream().filter(h -> (getProvincia(h.getLatitud(), h.getLongitud()) != null))
                .collect(Collectors.groupingBy(h -> getProvincia(h.getLatitud(), h.getLongitud()), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");
        return new ProvinciaConMasHechosPorColeccion(provincia,coleccion);
    }

    public String getProvincia(Float latitud, Float longitud) {
        return geocodingService.obtenerProvincia(latitud, longitud);
    }

    //¿Cuál es la categoría con mayor cantidad de hechos reportados?
    public CategoriaConMasHechos estadisticaCategoriaMasReportada() {
        String categoria = repositorioHechos.obtenerCategoriaConMasHechos().orElse("N/A");
        return new CategoriaConMasHechos(categoria);
    }
}