package domain.business.criterio;
import domain.business.FuentesDeDatos.FuenteDeDatos;
import domain.business.incidencias.Hecho;
import lombok.Getter;

public class CriterioFuenteDeDatos implements Criterio{

  @Getter
  private FuenteDeDatos fuenteDeDatos;

  public CriterioFuenteDeDatos(FuenteDeDatos fuenteDeDatos) {
    this.fuenteDeDatos = fuenteDeDatos;
  }

  @Override
  public boolean cumple(Hecho hecho) {

    return hecho.getFuenteDeDatos().equals(this.getFuenteDeDatos());
  }
}
