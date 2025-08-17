package Agregador.business.Consenso;


import java.util.ArrayList;
import Agregador.business.Agregador.*;
import Agregador.business.Colecciones.*;
import Agregador.business.Hechos.*;

public class MayoriaSimple implements Consenso {
  //si al menos la mitad de las fuentes contienen el mismo hecho, se lo considera
  //consensuado;
  @Override
  public boolean esConsensuado(Hecho hecho) {
//    int apariciones = 0;
//    for (FuenteDeDatos fuente : fuentes) {
//      for (Hecho h : fuente.getHechos()) {
//        if (h.equals(hecho)) {
//          apariciones++;
//          break;
//        }
//      }
//    }
//    return apariciones >= (fuentes.size() / 2.0);
    return true;
  }
}