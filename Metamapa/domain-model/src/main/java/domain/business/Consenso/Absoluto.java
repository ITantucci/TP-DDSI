package domain.business.Consenso;

import DTO.FuenteDeDatosDTO;
import DTO.HechoDTO;
import domain.business.FuentesDeDatos.FuenteDeDatos;
import domain.business.incidencias.Hecho;
import java.util.List;

public class Absoluto implements Consenso{
// si todas las fuentes contienen el mismo, se lo considera consensuado.
  @Override
  public boolean esConsensuado(HechoDTO hecho, List<FuenteDeDatosDTO> fuentes) {
    for (FuenteDeDatosDTO fuente : fuentes) {
      boolean contiene = fuente.getHechos().stream()
          .anyMatch(h -> h.equals(hecho));
      if (!contiene) return false;
    }
    return true;
  }
}