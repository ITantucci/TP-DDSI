package Usuarios.business.Usuarios;
// import Usuarios.business.tiposSolicitudes.SolicitudEdicion;
// import Usuarios.business.tiposSolicitudes.SolicitudEliminacion;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

public class Perfil {
  @Getter
  private String nombre;
  @Getter
  private String apellido;
  @Getter
  private Integer edad;
  @Getter
  private List<Integer> solicitudesDeEliminacion;
  @Getter
  private List<Integer> solicitudesDeEdicion;
  public Perfil(){}
  public Perfil(String nombre, String apellido, Integer edad) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.edad = edad;
    this.solicitudesDeEliminacion = new ArrayList<>();
    this.solicitudesDeEdicion = new ArrayList<>();

  }

 /* public void agregarSolicitudEliminacion(SolicitudEliminacion solicitud) {
    solicitudesDeEliminacion.add(solicitud);
  }

  public void agregarSolicitudEdicion(SolicitudEdicion solicitud) {
    solicitudesDeEdicion.add(solicitud);
  }*/
}