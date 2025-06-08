package domain.business.tiposSolicitudes;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class SolicitudEliminacion extends Solicitud{
  private String motivo;

  @Override
  public void aceptarSolicitud(){
    throw new UnsupportedOperationException("Not supported yet.");
  }
  public void rechazarSolicitud(){
    throw new UnsupportedOperationException("Not supported yet.");
  }
}