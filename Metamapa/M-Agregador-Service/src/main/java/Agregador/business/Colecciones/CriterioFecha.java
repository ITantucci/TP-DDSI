package Agregador.business.Colecciones;

import java.time.LocalDate;
import lombok.Getter;
import Agregador.business.Hechos.Hecho;
public class CriterioFecha extends Criterio {
  @Getter
  public LocalDate fechaDesde;
  @Getter
  public LocalDate fechaHasta;

  public CriterioFecha(LocalDate fechaDesde, LocalDate fechaHasta,boolean inclusion) {
    this.fechaDesde = fechaDesde;
    this.fechaHasta = fechaHasta;
    this.inclusion = inclusion;
  }

  public boolean cumple(Hecho hechoAValidar) {
    LocalDate fechaAValidar = hechoAValidar.getFechaHecho();
    if (this.getFechaHasta() == null) return !fechaAValidar.isBefore(this.getFechaDesde());
    if (this.getFechaDesde() == null) return !fechaAValidar.isAfter(this.getFechaHasta());
    return inclusion == (!fechaAValidar.isBefore(this.getFechaDesde()) && !fechaAValidar.isAfter(this.getFechaHasta()));
  }
}
