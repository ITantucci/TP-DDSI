package Agregador.business.deprecado.criterio;

import Agregador.business.deprecado.incidencias.Hecho;

public interface Criterio {
  /**
   * @param hecho el objeto a evaluar
   * @return true si el hecho cumple el criterio, false en caso contrario
   */
  boolean cumple(Hecho hecho);
}