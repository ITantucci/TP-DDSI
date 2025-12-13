package Estadistica.business.Colecciones;
import Estadistica.business.Hechos.Hecho;
import jakarta.persistence.Entity;
import jakarta.persistence.criteria.*;
import lombok.Getter;

@Entity
public class CriterioCategoria extends Criterio {
  @Getter
  private String categoria;

  public CriterioCategoria(String categoria, boolean inclusion) {
    this.categoria = categoria;
    this.inclusion = inclusion;
  }

  public CriterioCategoria() {}

  @Override
  public boolean cumple(Hecho hechoAValidar){
    String categoriaAValidar = hechoAValidar.getCategoria();
    return inclusion == this.getCategoria().equalsIgnoreCase(categoriaAValidar);
  }
  public Predicate toPredicate(Root<Hecho> root, CriteriaBuilder cb) {
    Predicate igual = cb.equal(cb.lower(root.get("categoria")), categoria.toLowerCase());
    return inclusion ? igual : cb.not(igual);
  }
}