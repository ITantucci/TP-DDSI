package Agregador.Service;
import Agregador.business.Hechos.Hecho;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import Agregador.business.Consenso.*;
import java.util.ArrayList;
import Agregador.persistencia.*;

@Service
@RequiredArgsConstructor
public class ServiceConsenso {
  private final RepositorioConsenso repositorioConsenso;
  private final RepositorioHechos repositorioHechos;

  public void consensuarHechos() {
    ArrayList<Hecho> hechos = (ArrayList<Hecho>) repositorioHechos.findAll();
    ArrayList<Consenso> consensos = (ArrayList<Consenso>) repositorioConsenso.findAll();

    hechos.forEach(h -> consensos.forEach(c -> {
      if (c.esConsensuado(h, hechos)) {
        h.agregarConsenso(c);
        System.out.println("Consenso: " + c);
      }
    }));


  }
}