package Agregador.persistencia;

import Agregador.business.Hechos.Hecho;
import lombok.Getter;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RepositorioHechos {
  @Getter
  private ArrayList<Hecho> hechos = new ArrayList<>();

  public List<Hecho> getHechos() {
    return hechos;
  }
  public void save(Hecho h) {
    hechos.add(h);
  }

  public Hecho findHecho(int id) {
    return (hechos.stream().filter(h -> h.getId().equals(id)).findFirst()).get();
  }

  public void modificarHecho(Hecho hecho) {
    //TODO implementar, no creo que haga falta
  }
}