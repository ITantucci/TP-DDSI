package Agregador.business.Colecciones;
import lombok.Getter;
import Agregador.business.Hechos.Hecho;
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Criterio {
  @Id
  Integer id;
  @Getter
  boolean inclusion;

  public boolean cumple(Hecho hecho) {
    return inclusion;
  }
}