package Agregador.business.Colecciones;

import lombok.Getter;
import Agregador.business.Hechos.Hecho;
import java.util.Objects;

public class CriterioFuenteDeDatos extends Criterio {
  @Getter
  private Integer idFuenteDeDatos;

  public CriterioFuenteDeDatos(Integer idFuenteDeDatos, boolean inclusion) {
    this.idFuenteDeDatos = idFuenteDeDatos;
    this.inclusion = inclusion;
  }

  @Override
  public boolean cumple(Hecho hechoAValidar){
    //BigInteger idFuenteDeDatosAValidar = hechoAValidar.getId();
    //return this.getIdFuenteDeDatos().equals(idFuenteDeDatosAValidar);
    return inclusion == Objects.equals(hechoAValidar.getIdFuente(), this.idFuenteDeDatos);
  }
}