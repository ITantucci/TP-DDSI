package Agregador.business.Consenso;

import Agregador.business.Hechos.Hecho;
import java.util.List;
import jakarta.persistence.*;
//TODO linkear las fuentes de domain en este modulo o crear una carpeta con la clase a usar
@Entity
@DiscriminatorValue("MAYORIA_SIMPLE")
public class MayoriaSimple extends Consenso {
  public MayoriaSimple() {
    super("MayoriaSimple");
  }

  //si al menos la mitad de las fuentes contienen el mismo hecho, se lo consideraconsensuado;
  @Override
  public boolean esConsensuado(Hecho hecho, List<Hecho> hechos, int cantFuentes) {
    int apariciones = 0;
    for (Hecho h : hechos) {
      if (Consenso.sonIguales(hecho, h))
        apariciones++;
    }
    return apariciones >= (cantFuentes / 2.0);
  }
}