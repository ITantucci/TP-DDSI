package Estadistica.business.Colecciones;
import Estadistica.business.Hechos.Hecho;
import jakarta.persistence.Entity;
import jakarta.persistence.criteria.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class CriterioFechaReportaje extends Criterio {
  private LocalDateTime desde;
  private LocalDateTime hasta;

  public CriterioFechaReportaje(LocalDateTime desde, LocalDateTime hasta,boolean inclusion) {
    this.desde = desde;
    this.hasta = hasta;
    this.inclusion = inclusion;
  }

  public CriterioFechaReportaje() {}

  public boolean cumple(Hecho hecho) {
    LocalDateTime fecha = hecho.getFechaCarga();
    return  inclusion == ((desde == null || !fecha.isBefore(this.getDesde())) &&
        (hasta == null || !fecha.isAfter(this.getHasta())));
  }

  public Predicate toPredicate(Root<Hecho> root, CriteriaBuilder cb) {
    Predicate predicate = null;

    if (desde != null && hasta != null) {
      predicate = cb.between(root.get("fechaCarga"), desde, hasta);
    } else if (desde != null) {
      predicate = cb.greaterThanOrEqualTo(root.get("fechaCarga"), desde);
    } else if (hasta != null) {
      predicate = cb.lessThanOrEqualTo(root.get("fechaCarga"), hasta);
    }

    if (predicate == null) {
      return cb.conjunction(); // no hay filtro
    }

    return inclusion ? predicate : cb.not(predicate);
  }
}