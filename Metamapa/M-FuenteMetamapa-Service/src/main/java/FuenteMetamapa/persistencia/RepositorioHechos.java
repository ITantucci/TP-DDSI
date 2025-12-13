package FuenteMetamapa.persistencia;
import FuenteMetamapa.business.Hechos.Hecho;
import java.util.*;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioHechos {
  private List<Hecho> hechos = new ArrayList<>();

  public RepositorioHechos() {
  }

  public List<Hecho> findAll() {
    return hechos;
  }

  public void save(Hecho hecho) {
    hechos.add(hecho);
  }

  public Optional<Hecho> findById(Integer id) {
    return hechos.stream().filter(hecho -> hecho.getId().equals(id)).findFirst();
  }

  public void saveAll(List<Hecho> hechos) {
    this.hechos.addAll(hechos);
  }
}