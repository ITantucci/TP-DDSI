package Agregador.business.deprecado.criterio;
import lombok.Getter;
import Agregador.business.deprecado.incidencias.Hecho;
import Agregador.business.deprecado.incidencias.TipoMultimedia;

public class CriterioMultimedia implements Criterio {
  @Getter
  private TipoMultimedia tipoMultimedia;

  public CriterioMultimedia(TipoMultimedia tipoMultimedia) {
    this.tipoMultimedia = tipoMultimedia;
  }

  @Override
  public boolean cumple(Hecho hecho) {
    return hecho.getMultimedia().stream().anyMatch(m-> getTipoMultimedia() == this.getTipoMultimedia());
  }
}