package Agregador.business.Colecciones;

import lombok.Getter;
import Agregador.business.Hechos.Hecho;

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

