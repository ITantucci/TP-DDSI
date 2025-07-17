package Agregador.business.deprecado.criterio;
import lombok.Getter;
import Agregador.business.deprecado.incidencias.Hecho;
import Agregador.business.deprecado.incidencias.Ubicacion;

public class CriterioUbicacion implements Criterio {
  @Getter
  private Float latitud;
  @Getter
  private Float longitud;

  public CriterioUbicacion(Float latitud, Float longitud) {
    this.latitud = latitud;
    this.longitud = longitud;
  }

  @Override
  public boolean cumple(Hecho hechoAValidar) {
    Ubicacion ubicacionAValidar = hechoAValidar.getUbicacion();
    return ubicacionAValidar.esIgual(this.getLatitud(), this.getLongitud());
  }
}
