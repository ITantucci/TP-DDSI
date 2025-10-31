package Estadistica.persistencia;

import org.springframework.data.jpa.repository.JpaRepository;
import Estadistica.business.Estadistica.Coleccion;
import java.util.*;

public interface RepositorioColecciones extends JpaRepository<Coleccion, UUID>,RepositorioColeccionesCustom{
  //List<Criterio> getCriteriosColeccion(UUID id);
  //Optional<Coleccion> getColeccion(UUID id);
}