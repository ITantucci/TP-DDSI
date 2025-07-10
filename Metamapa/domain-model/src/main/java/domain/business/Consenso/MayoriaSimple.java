package domain.business.Consenso;

import DTO.FuenteDeDatosDTO;
import DTO.HechoDTO;
import domain.business.FuentesDeDatos.FuenteDeDatos;
import domain.business.incidencias.Hecho;
import java.util.List;

public class MayoriaSimple implements Consenso{
  //si al menos la mitad de las fuentes contienen el mismo hecho, se lo considera
  //consensuado;
  @Override
  public boolean esConsensuado(HechoDTO hecho, List<FuenteDeDatosDTO> fuentes) {
    int apariciones = 0;
    for (FuenteDeDatosDTO fuente : fuentes) {
      for (HechoDTO h : fuente.getHechos()) {
        if (h.equals(hecho)) {
          apariciones++;
          break;
        }
      }
    }
    return apariciones >= (fuentes.size() / 2.0);
  }
}