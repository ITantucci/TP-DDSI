package Estadistica.persistencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import java.util.Optional;
import jakarta.persistence.criteria.Predicate;
import org.springframework.stereotype.Repository;
import Estadistica.business.Estadistica.Estadistica;

public class RepositorioEstadisticasImpl implements RepositorioEstadisticasCustom {

  @PersistenceContext
  private EntityManager em;



  @Override
  public <T extends Estadistica> Optional<T> obtenerMasNueva(
          Class<T> clazz,
          Map<String, Object> filtros
  ) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<T> query = cb.createQuery(clazz);
    Root<T> root = query.from(clazz);

    List<Predicate> predicates = new ArrayList<>();

    if (filtros != null) {
      filtros.forEach((campo, valor) -> {
        if (valor != null) {
          predicates.add(cb.equal(root.get(campo), valor));
        }
      });
    }

    query.select(root)
            .where(predicates.toArray(new Predicate[0]))
            .orderBy(cb.desc(root.get("fechaDeMedicion")));

    return em.createQuery(query)
            .setMaxResults(1)
            .getResultStream()
            .findFirst();
  }
}
