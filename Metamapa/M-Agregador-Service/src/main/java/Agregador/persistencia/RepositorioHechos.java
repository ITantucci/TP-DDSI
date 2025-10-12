package Agregador.persistencia;
import Agregador.business.Colecciones.Criterio;
import Agregador.business.Hechos.Hecho;
import Agregador.business.Consenso.*;
import org.springframework.stereotype.Repository;
import java.math.BigInteger;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public class RepositorioHechos extends JpaRepository<Hecho, Integer> {


  public List<Hecho> findByCategoriaAndEliminadoFalse(String categoria) {
    return this.findAll().stream()
        .filter(h -> h.getCategoria() != null
            && h.getCategoria().equalsIgnoreCase(categoria))
        .filter(h -> h.getEliminado() == null || !h.getEliminado())
        .toList();
  }



  public void updateHecho(Hecho h) {
    Optional<Hecho> existingHechoOpt = findById(h.getId());
    existingHechoOpt.ifPresent(s -> hechos.remove(s));
    hechos.add(h);

  }

  public List<Hecho> filtrarPorCriterios(List<Criterio> criterios, Consenso consenso) {
    List<Hecho> filtrados = this.findAll();
    if( consenso != null ) {
      filtrados = filtrados.stream().filter(h -> h.estaConsensuado(consenso)).toList();
    }
    return filtrados.stream()
        .filter(h -> criterios.stream().allMatch(c -> c.cumple(h)))
        .toList();
  }
}