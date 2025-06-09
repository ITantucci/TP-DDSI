package domain.business.tiposSolicitudes;
import domain.business.incidencias.Hecho;
import lombok.Getter;


public class SolicitudEliminacion extends Solicitud{

  @Getter
  public String motivo;

  public SolicitudEliminacion(Hecho hechoAfectado, EstadoSolicitud estado, String motivo) {
    super(hechoAfectado, estado);
    this.motivo = motivo;
  }

  @Override
  public void aceptarSolicitud(){
    throw new UnsupportedOperationException("Not supported yet.");
  }
  public void rechazarSolicitud(){
    throw new UnsupportedOperationException("Not supported yet.");
  }
}