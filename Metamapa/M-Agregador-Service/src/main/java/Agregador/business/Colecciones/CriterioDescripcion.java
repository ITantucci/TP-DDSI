package Agregador.business.Colecciones;

import lombok.Getter;
import Agregador.business.Hechos.Hecho;
public class CriterioDescripcion extends Criterio {
  @Getter
  private String descripcion;

  public CriterioDescripcion(String descripcion, boolean inclusion) {
    this.descripcion = descripcion;
    this.inclusion = inclusion;
  }
  @Override
  public boolean cumple(Hecho hechoAValidar){
    String descripcionAValidar = hechoAValidar.getDescripcion();
    return inclusion == this.descripcion.equals(descripcionAValidar);
  }
}
