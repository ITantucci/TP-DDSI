package domain.business.criterio;
import DTO.HechoDTO;
import domain.business.incidencias.Hecho;

public interface Criterio {
  /**
   * @param hecho el objeto a evaluar
   * @return true si el hecho cumple el criterio, false en caso contrario
   */
  boolean cumple(HechoDTO hecho);
}