package Agregador.persistencia;

import Agregador.DTO.FiltrosHechosDTO;

import Agregador.business.Colecciones.Criterio;
import Agregador.business.Consenso.Consenso;
import org.springframework.data.domain.Page;
import Agregador.business.Hechos.Hecho;
import org.springframework.data.domain.Pageable; // 💡 DEBE SER ESTE IMPORT
import java.util.List;

public interface RepositorioHechosCustom {
  List<Hecho> filtrarPorCriterios(List<Criterio> criterios, Consenso consenso);
  List<Criterio> construirCriterios(FiltrosHechosDTO filtros, boolean incluir);
  List<Hecho> buscarPorTextoLibre(String textoBusqueda);
  Page<Hecho> findAll(Pageable pageable);
}
