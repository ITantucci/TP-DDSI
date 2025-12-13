package Estadistica.business.Colecciones;
import Estadistica.business.Hechos.Hecho;
import jakarta.persistence.Entity;
import jakarta.persistence.criteria.*;
import lombok.Getter;

@Entity
public class CriterioTitulo extends Criterio {
  @Getter
  private String titulo;

  public CriterioTitulo(String titulo, boolean inclusion) {
    this.titulo = titulo;
    this.inclusion = inclusion;
  }

  public CriterioTitulo() {}

  @Override
  public boolean cumple(Hecho hechoAValidar){
    String tituloAValidar = hechoAValidar.getTitulo();
    return inclusion == this.getTitulo().equals(tituloAValidar);
  }

  public Predicate toPredicate(Root<Hecho> root, CriteriaBuilder cb) {
    Predicate igual = cb.equal(root.get("titulo"), titulo);
    return inclusion ? igual : cb.not(igual);
  }
}