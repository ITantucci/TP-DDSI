package Agregador.business.deprecado.Usuarios;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import Agregador.business.deprecado.tiposSolicitudes.SolicitudEdicion;
import Agregador.business.deprecado.tiposSolicitudes.SolicitudEliminacion;

public class Perfil {
  @Getter
  private String nombre;
  @Getter
  private String apellido;
  @Getter
  private Integer edad;
  @Getter
  private List<SolicitudEliminacion> solicitudesDeEliminacion;
  @Getter
  private List<SolicitudEdicion> solicitudesDeEdicion;
  public Perfil(){}
  public Perfil(String nombre, String apellido, Integer edad) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.edad = edad;
    this.solicitudesDeEliminacion = new ArrayList<>();
    this.solicitudesDeEdicion = new ArrayList<>();

  }

  public void agregarSolicitudEliminacion(SolicitudEliminacion solicitud) {
    solicitudesDeEliminacion.add(solicitud);
  }

  public void agregarSolicitudEdicion(SolicitudEdicion solicitud) {
    solicitudesDeEdicion.add(solicitud);
  }
}