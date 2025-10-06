package Agregador.business.Consenso;

import Agregador.business.Hechos.Hecho;
import java.util.ArrayList;

public class Absoluto extends Consenso {
// si todas las fuentes contienen el mismo, se lo considera consensuado.
  @Override
  public boolean esConsensuado(Hecho hecho, ArrayList<Hecho> hechos) {
    return hechos.stream().allMatch(h -> h.getTitulo() == hecho.getTitulo());
  }
}