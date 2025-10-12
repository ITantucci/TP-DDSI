package Agregador.business.Colecciones;
import java.util.Objects;
import lombok.Getter;
import Agregador.business.Hechos.Hecho;

public class CriterioUbicacion extends Criterio {
  @Getter
  private Float latitud;
  @Getter
  private Float longitud;

  public CriterioUbicacion(Float latitud, Float longitud, boolean inclusion) {
    this.latitud = latitud;
    this.longitud = longitud;
    this.inclusion = inclusion;
  }

  @Override
  public boolean cumple(Hecho hechoAValidar) {
    //TODO: APROXIMAR UN RANGO
    return inclusion == Objects.equals(hechoAValidar.getLatitud(), latitud) && Objects.equals(hechoAValidar.getLongitud(), longitud);
  }
}
