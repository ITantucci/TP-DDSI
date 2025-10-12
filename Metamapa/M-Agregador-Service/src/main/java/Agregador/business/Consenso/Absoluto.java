package Agregador.business.Consenso;

import Agregador.business.Hechos.Hecho;
import java.util.ArrayList;

public class Absoluto extends Consenso {
// si todas las fuentes contienen el mismo, se lo considera consensuado.
  @Override
  public boolean esConsensuado(Hecho hecho, ArrayList<Hecho> hechos) {
    int apariciones = 0;
    int cantFuentes = Consenso.contarFuentesDeDatos(hechos);
    for (Hecho h : hechos) {
      if (Consenso.sonIguales(hecho, h)) {
        apariciones++;
        break;
      }
    }
    return apariciones >= cantFuentes;
  }
}