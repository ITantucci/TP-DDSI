package Estadistica.persistencia;
import Estadistica.business.Hechos.Hecho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import Estadistica.business.Colecciones.*;
import java.math.BigInteger;
import java.util.List;

@Repository
public interface RepositorioHechos extends JpaRepository<Hecho, BigInteger>, RepositorioHechosCustom {
  //List<Hecho> filtrarPorCriterios(List<Criterio> criterios, Consenso consenso);
  //List<Hecho> findByCategoriaIgnoreCaseAndEliminadoFalse(String categoria);
  List<Hecho> filtrarPorCriterios(List<Criterio> criterios);

  List<Hecho> findByCategoriaIgnoreCase(String categoria);
  List<Hecho> findByCategoria(String categoria);
  List<String> obtenerCategorias();
}