package Estadistica.business.Estadistica.Criterios;
import Estadistica.business.Estadistica.Hecho;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import lombok.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter @Setter
public abstract class Criterio {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "criterio_seq")
  @SequenceGenerator(name = "criterio_seq", sequenceName = "criterio_seq", allocationSize = 1)
  private Integer id;
  @Column(nullable = false)
  Boolean inclusion;

  public Criterio() {}

  public Criterio(Boolean inclusion) {
    this.inclusion = inclusion;
  }

  public boolean cumple(Hecho hecho) {
    return inclusion;
  }

  public Predicate toPredicate(Root<Hecho> root, CriteriaBuilder cb) {
    return null;
  }
}