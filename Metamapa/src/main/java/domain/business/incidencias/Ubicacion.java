package domain.business.incidencias;

import lombok.Getter;

public class Ubicacion {
  @Getter
  private final Float latitud;
  @Getter
  private final Float longitud;
  
  public Ubicacion(Float latitud, Float longitud) {
    this.latitud = latitud;
    this.longitud = longitud;
  }
//TODO: Agregar metodo a diagrama de clases
  public boolean esIgual(Ubicacion ubicacion) {
    Float latitudAValidar = ubicacion.getLatitud();
    Float longitudAValidar = ubicacion.getLongitud();
    return latitudAValidar.equals(this.getLatitud()) && longitudAValidar.equals(this.getLongitud());
  }
}
