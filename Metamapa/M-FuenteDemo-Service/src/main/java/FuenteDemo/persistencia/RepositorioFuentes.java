package FuenteDemo.persistencia;
import FuenteDemo.business.FuentesDeDatos.FuenteDemo;
import java.util.*;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioFuentes  {
  private List<FuenteDemo> fuentes = new ArrayList<>();

    public RepositorioFuentes() {
    }
    public List<FuenteDemo> findAll() {
        return fuentes;
    }
    public void save(FuenteDemo fuente) {
        fuentes.add(fuente);
    }
    public Optional<FuenteDemo> findById(Integer id) {
        return fuentes.stream().filter(fuente -> fuente.getId().equals(id)).findFirst();
    }

    public void saveAll(List<FuenteDemo> fuentes) {
        this.fuentes.addAll(fuentes);
    }
}