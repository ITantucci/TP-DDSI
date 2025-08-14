package Agregador.business.Consenso;

import java.util.List;
import Agregador.business.deprecado.FuentesDeDatos.FuenteDeDatos;
import Agregador.business.deprecado.incidencias.Hecho;

public interface Consenso {
  boolean esConsensuado(Hecho hecho, List<FuenteDeDatos> fuentes);

  static Consenso stringToConsenso(String algoritmo) {
        switch (algoritmo)
        {
          case "Absoluto": return new Absoluto();
          case "MultiplesMenciones": return new MultiplesMenciones();
          case "MayoriaSimple": return new MayoriaSimple();
          default: throw new IllegalArgumentException("Tipo de consenso no valido");
        }
  }
}