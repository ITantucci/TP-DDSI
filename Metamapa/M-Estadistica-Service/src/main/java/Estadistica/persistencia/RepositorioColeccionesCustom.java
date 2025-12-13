package Estadistica.persistencia;

import Estadistica.business.Colecciones.Coleccion;
import Estadistica.business.Colecciones.Criterio;
import java.util.*;

public interface RepositorioColeccionesCustom {
  List<Criterio> getCriteriosColeccion(UUID id);
  Optional<Coleccion> getColeccion(UUID id);
}
