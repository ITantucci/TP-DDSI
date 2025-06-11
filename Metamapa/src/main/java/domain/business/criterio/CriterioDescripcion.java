package domain.business.criterio;
import domain.business.incidencias.Hecho;
import lombok.Getter;


public class CriterioDescripcion implements Criterio {
  @Getter
  private String descripcion;

  public CriterioDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public boolean cumple(Hecho hechoAValidar){
    return this.descripcion.equals(hechoAValidar.getDescripcion());
  }
}
