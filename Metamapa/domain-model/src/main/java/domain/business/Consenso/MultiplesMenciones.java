package domain.business.Consenso;

import DTO.FuenteDeDatosDTO;
import DTO.HechoDTO;
import domain.business.FuentesDeDatos.FuenteDeDatos;
import domain.business.incidencias.Hecho;
import java.util.List;

public class MultiplesMenciones implements Consenso{
  //si al menos dos fuentes contienen un mismo hecho y ninguna otra fuente
  //contiene otro de igual título pero diferentes atributos, se lo considera consensuado;
  @Override
  public boolean esConsensuado(HechoDTO hecho, List<FuenteDeDatosDTO> fuentes) {
    int aparicionesExactas = 0;
    boolean hayConflictos = false;
    for (FuenteDeDatosDTO fuente : fuentes) {
      for (HechoDTO h : fuente.getHechos()) {
        if (h.getTitulo().equalsIgnoreCase(hecho.getTitulo())) {
          if (h.equals(hecho)) {
            aparicionesExactas++;
          } else {
            hayConflictos = true;
          }
        }
      }
    }
    return aparicionesExactas >= 2 && !hayConflictos;
  }
}