package Agregador.business.Consenso;

import java.util.List;
import Agregador.business.deprecado.FuentesDeDatos.FuenteDeDatos;
import Agregador.business.deprecado.incidencias.Hecho;

public class Absoluto implements Consenso {
// si todas las fuentes contienen el mismo, se lo considera consensuado.
  @Override
  public boolean esConsensuado(Hecho hecho, List<FuenteDeDatos> fuentes) {
    for (FuenteDeDatos fuente : fuentes) {
      boolean contiene = fuente.getHechos().stream()
          .anyMatch(h -> h.equals(hecho));
      if (!contiene) return false;
    }
    return true;
  }
}