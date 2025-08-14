package Agregador.business.Colecciones;
import lombok.Getter;
import Agregador.business.deprecado.incidencias.Hecho;

public class CriterioCategoria implements Criterio {
  @Getter
  private String categoria;

  public CriterioCategoria(String categoria) {
    this.categoria = categoria;
  }
  @Override
  public boolean cumple(Hecho hechoAValidar){
    String categoriaAValidar = hechoAValidar.getCategoria();

    return this.getCategoria().equalsIgnoreCase(categoriaAValidar);
  }
}
