package Estadistica.persistencia;

import Estadistica.business.Estadistica.Coleccion;
import Estadistica.business.Estadistica.Criterios.Criterio;
import java.util.*;

public interface RepositorioColeccionesCustom {
  List<Criterio> getCriteriosColeccion(UUID id);
  Optional<Coleccion> getColeccion(UUID id);
}
