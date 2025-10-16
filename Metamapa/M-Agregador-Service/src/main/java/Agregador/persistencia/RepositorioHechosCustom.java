package Agregador.persistencia;
import Agregador.DTO.FiltrosHechosDTO;
import Agregador.business.Colecciones.Criterio;
import Agregador.business.Consenso.Consenso;
import Agregador.business.Hechos.Hecho;
import java.util.List;

public interface RepositorioHechosCustom {
  List<Hecho> filtrarPorCriterios(List<Criterio> criterios, Consenso consenso);
   List<Criterio> construirCriterios(FiltrosHechosDTO filtros, boolean incluir);
}
