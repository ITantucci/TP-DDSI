package Agregador.business.deprecado.criterio;

import lombok.Getter;
import Agregador.business.deprecado.incidencias.Hecho;

public class CriterioDescripcion implements Criterio {
  @Getter
  private String descripcion;

  public CriterioDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }
  @Override
  public boolean cumple(Hecho hechoAValidar){
    String descripcionAValidar = hechoAValidar.getDescripcion();

    return this.descripcion.equals(descripcionAValidar);
  }
}
