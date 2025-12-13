package FuenteMetamapa.persistencia;
import FuenteMetamapa.business.FuentesDeDatos.*;
import java.util.*;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioFuentes {
  private List<FuenteMetamapa> fuentes = new ArrayList<>();

  public RepositorioFuentes() {
  }

  public List<FuenteMetamapa> findAll() {
    return fuentes;
  }

  public void save(FuenteMetamapa fuente) {
    fuentes.add(fuente);
  }

  public Optional<FuenteMetamapa> findById(Integer id) {
    return fuentes.stream().filter(fuente -> fuente.getId().equals(id)).findFirst();
  }

  public void saveAll(List<FuenteMetamapa> fuentes) {
    this.fuentes.addAll(fuentes);
  }
}