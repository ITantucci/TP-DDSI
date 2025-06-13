package infrastructure.dto;

import domain.business.criterio.*;
import lombok.Getter;
import lombok.Setter;
import domain.business.incidencias.Hecho;
import domain.business.incidencias.Ubicacion;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;


@Getter @Setter
public class FiltroHechosDTO {
  private String categoria;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate fechaReporteDesde;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate fechaReporteHasta;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate fechaAcontecimientoDesde;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate fechaAcontecimientoHasta;

  private Float latitud;
  private Float longitud;


  public List<Criterio> aCriterios() {
    List<Criterio> criterios = new ArrayList<>();
    if (categoria != null) criterios.add(new CriterioCategoria(categoria));
    if (latitud != null && longitud != null) criterios.add(new CriterioUbicacion(latitud, longitud));
    if (fechaAcontecimientoDesde != null || fechaAcontecimientoHasta != null)
      criterios.add(new CriterioFecha(fechaAcontecimientoDesde, fechaAcontecimientoHasta));
    if (fechaReporteDesde != null || fechaReporteHasta != null)
      criterios.add(new CriterioFechaReportaje(fechaReporteDesde, fechaReporteHasta));
    return criterios;
  }

}
