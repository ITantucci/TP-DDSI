package Estadistica.business;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class Estadistica {

  public void actualizarEstadisticas() {}
  public void generarEstadisticas() {}
  public void exportarDatos() {}
}
