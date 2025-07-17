package Agregador.business.deprecado.criterio;

import lombok.Getter;
import Agregador.business.deprecado.incidencias.Hecho;


public class CriterioFuenteDeDatos implements Criterio {
  @Getter
  private Integer idFuenteDeDatos;

  public CriterioFuenteDeDatos(Integer idFuenteDeDatos)
  {
    this.idFuenteDeDatos = idFuenteDeDatos;
  }
  @Override
  public boolean cumple(Hecho hechoAValidar){
    Integer idFuenteDeDatosAValidar = hechoAValidar.getId();

    return this.getIdFuenteDeDatos().equals(idFuenteDeDatosAValidar);
  }
}

