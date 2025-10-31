package Estadistica.persistencia;

import Estadistica.DTO.FiltrosHechosDTO;
import Estadistica.business.Consenso.Consenso;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class RepositorioHechosImpl implements RepositorioHechosCustom {
  @PersistenceContext
  private EntityManager em;

  @Override
  public List<Hecho> filtrarPorCriterios(List<Criterio> criterios, Consenso consenso) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Hecho> query = cb.createQuery(Hecho.class);
    Root<Hecho> root = query.from(Hecho.class);
    List<Predicate> predicates = new ArrayList<>();
    criterios.forEach(c -> predicates.add(c.toPredicate(root, cb)));
    query.select(root)
        .where(cb.and(predicates.toArray(new Predicate[0])));

    List<Hecho> filtrados = em.createQuery(query).getResultList();
    // aplicar criterios no SQL (como CriterioFuenteDeDatos) en memoria
    for (Criterio c : criterios) {
      filtrados = filtrados.stream()
              .filter(c::cumple)
              .toList();
    }
    if (consenso != null) {
      filtrados = filtrados.stream()
          .filter(h -> h.estaConsensuado(consenso))
          .toList();
    }
    return filtrados;
  }
  @Override
  public List<Criterio> construirCriterios(FiltrosHechosDTO filtros, boolean incluir) {
    List<Criterio> criterios = new ArrayList<>();
    if (incluir) {
      if (filtros.getTituloP() != null) criterios.add(new CriterioTitulo(filtros.getTituloP(), true));
      if (filtros.getDescripcionP() != null) criterios.add(new CriterioDescripcion(filtros.getDescripcionP(), true));
      if (filtros.getCategoriaP() != null) criterios.add(new CriterioCategoria(filtros.getCategoriaP(), true));
      if (filtros.getFechaReporteDesdeP() != null || filtros.getFechaReporteHastaP() != null)
        criterios.add(new CriterioFechaReportaje(filtros.getFechaReporteDesdeP(), filtros.getFechaReporteHastaP(), true));
      if (filtros.getFechaAcontecimientoDesdeP() != null || filtros.getFechaAcontecimientoHastaP() != null)
        criterios.add(new CriterioFecha(filtros.getFechaAcontecimientoDesdeP(), filtros.getFechaAcontecimientoHastaP(), true));
      if (filtros.getLatitudP() != null && filtros.getLongitudP() != null && filtros.getRadioP() != null)
        criterios.add(new CriterioUbicacion(filtros.getLatitudP(), filtros.getLongitudP(),filtros.getRadioP(), true));
      if (filtros.getTipoMultimediaP() != null) criterios.add(new CriterioMultimedia(TipoMultimedia.valueOf(filtros.getTipoMultimediaP()), true));
    } else {
      if (filtros.getTituloNP() != null) criterios.add(new CriterioTitulo(filtros.getTituloNP(), false));
      if (filtros.getDescripcionNP() != null) criterios.add(new CriterioDescripcion(filtros.getDescripcionNP(), false));
      if (filtros.getCategoriaNP() != null) criterios.add(new CriterioCategoria(filtros.getCategoriaNP(), false));
      if (filtros.getFechaReporteDesdeNP() != null || filtros.getFechaReporteHastaNP() != null)
        criterios.add(new CriterioFechaReportaje(filtros.getFechaReporteDesdeNP(), filtros.getFechaReporteHastaNP(), false));
      if (filtros.getFechaAcontecimientoDesdeNP() != null || filtros.getFechaAcontecimientoHastaNP() != null)
        criterios.add(new CriterioFecha(filtros.getFechaAcontecimientoDesdeNP(), filtros.getFechaAcontecimientoHastaNP(), false));
      if (filtros.getLatitudNP() != null && filtros.getLongitudNP() != null&& filtros.getRadioNP() != null)
        criterios.add(new CriterioUbicacion(filtros.getLatitudNP(), filtros.getLongitudNP(),filtros.getRadioNP(), false));
      if (filtros.getTipoMultimediaNP() != null) criterios.add(new CriterioMultimedia(TipoMultimedia.valueOf(filtros.getTipoMultimediaNP()), false));
    }
    return criterios;
  }
}