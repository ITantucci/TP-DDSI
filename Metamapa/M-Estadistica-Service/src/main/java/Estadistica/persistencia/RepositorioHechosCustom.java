package Estadistica.persistencia;

import Estadistica.business.Estadistica.Criterios.Criterio;
import Estadistica.business.Consenso.Consenso;
import Estadistica.business.Estadistica.Hecho;
import java.util.List;

public interface RepositorioHechosCustom {
  List<Hecho> filtrarPorCriterios(List<Criterio> criterios, Consenso consenso);
   //List<Criterio> construirCriterios(FiltrosHechosDTO filtros, boolean incluir);
}
