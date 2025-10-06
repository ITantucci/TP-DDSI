package Agregador.persistencia;

import Agregador.business.Consenso.Consenso;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import Agregador.business.Consenso.*;

@Repository
public class RepositorioConsenso {
  ArrayList<Consenso> consensos;
  RepositorioConsenso() {
    consensos = new ArrayList<Consenso>();
    consensos.add(new Absoluto());
    consensos.add(new MayoriaSimple());
    consensos.add(new MultiplesMenciones());
  }

  public ArrayList<Consenso> findAll() {
    return consensos;
  }

  public Consenso findById(int id) {
    return consensos.get(id);
  }

  public void save(Consenso consenso) {
    consensos.add(consenso);
  }

  public void delete(Consenso consenso) {
    consensos.remove(consenso);
  }

  public void update(Consenso consenso) {
    consensos.set(consensos.indexOf(consenso), consenso);
  }

}
