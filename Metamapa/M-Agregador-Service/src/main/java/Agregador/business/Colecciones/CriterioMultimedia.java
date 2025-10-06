package Agregador.business.Colecciones;
import lombok.Getter;
import Agregador.business.Hechos.Hecho;
import Agregador.business.Hechos.TipoMultimedia;

public class CriterioMultimedia extends Criterio {
  @Getter
  private TipoMultimedia tipoMultimedia;

  public CriterioMultimedia(TipoMultimedia tipoMultimedia, boolean inclusion) {
    this.tipoMultimedia = tipoMultimedia;
    this.inclusion = inclusion;
  }

  @Override
  public boolean cumple(Hecho hecho) {
    return inclusion == hecho.getMultimedia().stream().anyMatch(m-> getTipoMultimedia() == this.getTipoMultimedia());
  }
}