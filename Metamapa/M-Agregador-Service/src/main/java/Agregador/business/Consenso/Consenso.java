package Agregador.business.Consenso;

import java.util.ArrayListList;
import Agregador.business.Agregador.*;
import Agregador.business.Colecciones.*;
import Agregador.business.Hechos.*;

public interface Consenso {
  boolean esConsensuado(Hecho hecho);
  
  
  //TODO reimplementar los consensos sin la clase fuente de datos
  static Consenso stringToConsenso(String algoritmo) {
        switch (algoritmo)
        {
          case "Absoluto": return new Absoluto();
          case "MultiplesMenciones": return new MultiplesMenciones();
          case "MayoriaSimple": return new MayoriaSimple();
          default: throw new IllegalArgumentException("Tipo de consenso no valido");
        }
  }
  static Boolean sonIguales(Hecho hecho1,Hecho hecho2)
  {
    return true;
  }

}