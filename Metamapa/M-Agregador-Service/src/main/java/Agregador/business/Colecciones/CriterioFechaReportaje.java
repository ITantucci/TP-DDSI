package Agregador.business.Colecciones;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import Agregador.business.Hechos.Hecho;
@Getter @Setter
public class CriterioFechaReportaje extends Criterio {
  private final LocalDate desde;
  private final LocalDate hasta;

  public CriterioFechaReportaje(LocalDate desde, LocalDate hasta,boolean inclusion) {
    this.desde = desde;
    this.hasta = hasta;
    this.inclusion = inclusion;
  }

  public boolean cumple(Hecho hecho) {
    LocalDate fecha = hecho.getFechaCarga();
    return  inclusion == ((desde == null || !fecha.isBefore(this.getDesde())) &&
        (hasta == null || !fecha.isAfter(this.getHasta())));
  }
}