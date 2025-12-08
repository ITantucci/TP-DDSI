package Agregador.business.Consenso;

import Agregador.business.Hechos.Hecho;
import jakarta.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("ABSOLUTO")
public class Absoluto extends Consenso {
  public Absoluto() {
    super("Absoluto");
  }

  // si todas las fuentes contienen el mismo, se lo considera consensuado.
  @Override
  public boolean esConsensuado(Hecho hecho, List<Hecho> hechos, int cantFuentes) {
    int apariciones = 0;
    for (Hecho h : hechos) {
      if (Consenso.sonIguales(hecho, h))
        apariciones++;
    }
    return apariciones >= cantFuentes;
  }
}