package Agregador.business.Solicitudes;
import Agregador.business.Hechos.Hecho;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
public class SolicitudEliminacion extends Solicitud {
  @Getter
  public String motivo;

  public SolicitudEliminacion(Hecho hechoAfectado, String motivo) {
    super(hechoAfectado, EstadoSolicitud.PENDIENTE); //por defecto se inicializan pendientes
    Boolean esSpam;
    try {
      esSpam = DetectorDeSpam.esSpam(motivo);
    } catch (Exception e) {
      //a revisar que hacer en caso de que la API falle para detectar el spam
      esSpam = true;
    }
    if (esSpam) setEstado(EstadoSolicitud.SPAM); // EstadoSolicitud.RECHAZADA
    this.motivo = motivo;
  }

  public SolicitudEliminacion() {}

  @Override
  public void aceptarSolicitud() {
    if (getEstado() != EstadoSolicitud.PENDIENTE)
      throw new IllegalStateException("Solo se puede aprobar si está pendiente");
    super.aceptarSolicitud();
    getHechoAfectado().setEliminado(true);
  }

  public void rechazarSolicitud() {
    super.rechazarSolicitud();
  }
}