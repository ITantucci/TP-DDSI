package Agregador.business.Colecciones;
import lombok.Getter;
import Agregador.business.Hechos.Hecho;
public class CriterioCategoria extends Criterio {
  @Getter
  private String categoria;

  public CriterioCategoria(String categoria, boolean inclusion) {
    this.categoria = categoria;
    this.inclusion = inclusion;
  }

  @Override
  public boolean cumple(Hecho hechoAValidar){
    String categoriaAValidar = hechoAValidar.getCategoria();
    return inclusion == this.getCategoria().equalsIgnoreCase(categoriaAValidar);
  }
}
