package Agregador.persistencia;
import Agregador.business.Agregador.Agregador;
import Agregador.business.Colecciones.*;
import Agregador.business.Consenso.*;
import Agregador.business.Hechos.Hecho;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class RepositorioColecciones {
  private final ArrayList<Coleccion> colecciones = new ArrayList<>();

  // === Búsquedas básicas ===
  public Optional<Coleccion> findById(UUID uuid) {
    if (uuid == null) return Optional.empty();
    return colecciones.stream().filter(c -> uuid.equals(c.getHandle())).findFirst();
  }

  public List<Coleccion> findAll() {
    return colecciones;
  }

  // === Altas / Bajas / Modificaciones ===
  public void crear(Coleccion coleccion) {
    Objects.requireNonNull(coleccion, "coleccion no puede ser null");
    // Evita duplicado por UUID
    colecciones.removeIf(c -> c.getHandle().equals(coleccion.getHandle()));
    colecciones.add(coleccion);
  }

  /** Crea y devuelve el UUID de la colección creada (útil para Services/Controllers). */
  public UUID crear(String titulo, String descripcion, Consenso consenso, List<Criterio> criterios) {
    Coleccion c = new Coleccion(titulo, descripcion, consenso, (ArrayList)criterios);
    crear(c);
    return c.getHandle();
  }

  /** Reemplaza la colección por handle (upsert por UUID). */
  public void update(Coleccion coleccion) {
    Objects.requireNonNull(coleccion, "coleccion no puede ser null");
    colecciones.removeIf(c -> c.getHandle().equals(coleccion.getHandle()));
    colecciones.add(coleccion);
  }

  /** Elimina por UUID. */
  public boolean deleteById(UUID id) {
    if (id == null) return false;
    return colecciones.removeIf(c -> id.equals(c.getHandle()));
  }

  // === Cambios de configuración de la colección ===
  /** Cambia el consenso de la colección. */
  public boolean modificarConsenso(UUID id, Consenso consenso) {
    Objects.requireNonNull(id, "id no puede ser null");
    Objects.requireNonNull(consenso, "consenso no puede ser null");
    return findById(id).map(c -> { c.setConsenso(consenso); return true; }).orElse(false);
  }


  // === Gestión de criterios (pertenencia / no pertenencia) ===
  /**
   * Igual que obtenerHechos, pero te permite pasar criterios adicionales (ORIGINADOS en la vista/consulta).
   * Se aplican sumando a los definidos en la colección.
   */
  public List<Hecho> obtenerHechos(UUID idColeccion,
                                   List<Criterio> criteriosAdicionalP,
                                   List<Criterio> criteriosAdicionalNP,
                                   ModosDeNavegacion modo) {
    Coleccion col = findById(idColeccion).orElse(null);
    if (col == null) return List.of();

    List<Hecho> hechos = Agregador.getInstance().getListaHechos();
    return col.filtrarPorCriterios(
            new ArrayList<>(hechos),
            criteriosAdicionalP == null ? new ArrayList<>() : new ArrayList<>(criteriosAdicionalP),
            criteriosAdicionalNP == null ? new ArrayList<>() : new ArrayList<>(criteriosAdicionalNP),
            modo
    );
  }

  // === Helpers de conveniencia para Controllers/Services ===

  /** Renombra título y descripción (PUT/PATCH ligero). */
  public boolean actualizarMetadatos(UUID id, String nuevoTitulo, String nuevaDescripcion) {
    return findById(id).map(c -> {
      if (nuevoTitulo != null) c.setTitulo(nuevoTitulo);
      if (nuevaDescripcion != null) c.setDescripcion(nuevaDescripcion);
      return true;
    }).orElse(false);
  }

  // === Semilla / demo (opcional) ===
  // Descomentá si querés subir el servicio con una colección ejemplo
  /*
  public RepositorioColecciones() {
    // Ejemplo: crea una colección vacía con consenso Absoluto, sin criterios
    UUID demo = crear("Colección Demo", "Para probar endpoints",
        Consenso.ABSOLUTO,
        List.of(), List.of());
    modificarModoNavegacion(demo, ModosDeNavegacion.IRRESTRICTA);
  }
  */
}