package Agregador.business.Colecciones;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import Agregador.business.Hechos.Hecho;
@Getter @Setter
public class CriterioFechaReportaje implements Criterio {
  private final LocalDate desde;
  private final LocalDate hasta;

  public CriterioFechaReportaje(LocalDate desde, LocalDate hasta) {
    this.desde = desde;
    this.hasta = hasta;
  }

  public boolean cumple(Hecho hecho) {
    LocalDate fecha = hecho.getFechaCarga();
    return (desde == null || !fecha.isBefore(this.getDesde())) &&
        (hasta == null || !fecha.isAfter(this.getHasta()));
  }
}