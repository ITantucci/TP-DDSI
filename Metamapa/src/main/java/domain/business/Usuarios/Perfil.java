package domain.business.Usuarios;
import domain.business.tiposSolicitudes.SolicitudEdicion;
import domain.business.tiposSolicitudes.SolicitudEliminacion;
import java.util.List;

public class Perfil {
  private String nombre;
  private String apellido;
  private String edad;
  private List<SolicitudEliminacion> solicitudesDeEliminacion;
  private List<SolicitudEdicion> solicitudesDeEdicion;


  //TODO: cambiar getters y setters

  // getters

  public String getNombre() {
    return nombre;
  }

  public String getApellido() {
    return apellido;
  }

  public String getEdad() {
    return edad;
  }

  public List<SolicitudEliminacion> getSolicitudesDeEliminacion() {
    return solicitudesDeEliminacion;
  }

  public List<SolicitudEdicion> getSolicitudesDeEdicion() {
    return solicitudesDeEdicion;
  }


  //setters

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }

  public void setEdad(String edad) {
    this.edad = edad;
  }

  public void setSolicitudesDeEliminacion(List<SolicitudEliminacion> solicitudesDeEliminacion) {
    this.solicitudesDeEliminacion = solicitudesDeEliminacion;
  }

  public void setSolicitudesDeEdicion(List<SolicitudEdicion> solicitudesDeEdicion) {
    this.solicitudesDeEdicion = solicitudesDeEdicion;
  }


  //metodos

  public void agregarSolicitudEliminacion(SolicitudEliminacion solicitud) {
    solicitudesDeEliminacion.add(solicitud);
  }

  public void agregarSolicitudEdicion(SolicitudEdicion solicitud) {
    solicitudesDeEdicion.add(solicitud);
  }
}