package Estadistica.business.Colecciones;
import Estadistica.business.Hechos.Hecho;
import jakarta.persistence.Entity;
import jakarta.persistence.criteria.*;
import lombok.Getter;
import java.util.Objects;

@Entity
public class CriterioFuenteDeDatos extends Criterio {
  @Getter
  private Integer idFuenteDeDatos;

  public CriterioFuenteDeDatos(Integer idFuenteDeDatos, Boolean inclusion) {
    this.inclusion = inclusion;
    this.idFuenteDeDatos = idFuenteDeDatos;
  }

  public CriterioFuenteDeDatos() {}

  @Override
  public boolean cumple(Hecho hechoAValidar){
    //BigInteger idFuenteDeDatosAValidar = hechoAValidar.getId();
    //return this.getIdFuenteDeDatos().equals(idFuenteDeDatosAValidar);
    System.out.println("fuente del hecho " + hechoAValidar.getIdFuente());
    System.out.println("fuente a validar" + this.idFuenteDeDatos);
    System.out.println("inclusion" + this.inclusion);
    return inclusion == Objects.equals(hechoAValidar.getIdFuente(), this.idFuenteDeDatos);
  }

  @Override
  public Predicate toPredicate(Root<Hecho> root, CriteriaBuilder cb) {
    // No se puede hacer en SQL porque idFuente no es atributo persistido
    // Devolvemos un "true" genérico para no filtrar nada a nivel SQL
    return cb.conjunction(); // o cb.isTrue(cb.literal(true))
  }
}