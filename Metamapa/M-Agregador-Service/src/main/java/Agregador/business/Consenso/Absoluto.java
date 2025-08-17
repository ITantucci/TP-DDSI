package Agregador.business.Consenso;

import java.util.ArrayList;
import Agregador.business.Agregador.*;
import Agregador.business.Colecciones.*;
import Agregador.business.Hechos.*;

public class Absoluto implements Consenso {
// si todas las fuentes contienen el mismo, se lo considera consensuado.
  @Override
  public boolean esConsensuado(Hecho hecho) {

    ArrayList<Hecho> hechos = new ArrayList<Hecho>();
    if (!hechos.stream().allMatch(h -> {h.getTitulo() == hecho.getTitulo();})) return false;
    return true;
  }
}