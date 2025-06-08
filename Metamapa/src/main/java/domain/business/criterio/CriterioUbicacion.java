package domain.business.criterio;
import domain.business.incidencias.Hecho;
import domain.business.incidencias.Ubicacion;
import lombok.Getter;

public class CriterioUbicacion implements Criterio{
  @Getter
  private Ubicacion ubicacion;

  public CriterioUbicacion(Ubicacion ubicacion) {

    this.ubicacion = ubicacion;
  }

  public boolean cumple(Hecho hechoAValidar) {
    Ubicacion ubicacionAValidar = hechoAValidar.getUbicacion();

    return this.getUbicacion().esIgual(ubicacionAValidar);
  }
}
