package Agregador.business.deprecado.criterio;

import java.time.LocalDate;
import lombok.Getter;
import Agregador.business.deprecado.incidencias.Hecho;

public class CriterioFecha implements Criterio {
  @Getter
  public LocalDate fechaDesde;
  @Getter
  public LocalDate fechaHasta;

  public CriterioFecha(LocalDate fechaDesde, LocalDate fechaHasta) {
    this.fechaDesde = fechaDesde;
    this.fechaHasta = fechaHasta;
  }

  public boolean cumple(Hecho hechoAValidar) {

    LocalDate fechaAValidar = hechoAValidar.getFechaHecho();
    if (this.getFechaHasta() == null) return !fechaAValidar.isBefore(this.getFechaDesde());
    if (this.getFechaDesde() == null) return !fechaAValidar.isAfter(this.getFechaHasta());
    return !fechaAValidar.isBefore(this.getFechaDesde()) && !fechaAValidar.isAfter(this.getFechaHasta());

  }
}
