package Agregador.business.Colecciones;
import lombok.Getter;
import Agregador.business.Hechos.Hecho;

public class CriterioTitulo extends Criterio {
  @Getter
  private final String titulo;

  public CriterioTitulo(String titulo, boolean inclusion) {
    this.titulo = titulo;
    this.inclusion = inclusion;
  }

  @Override
  public boolean cumple(Hecho hechoAValidar){
    String tituloAValidar = hechoAValidar.getTitulo();
    return inclusion == this.getTitulo().equals(tituloAValidar);
  }
}