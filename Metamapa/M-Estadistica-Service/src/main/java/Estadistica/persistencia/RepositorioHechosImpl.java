package Estadistica.persistencia;

import Estadistica.business.Colecciones.Criterio;
import Estadistica.business.Hechos.Hecho;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class RepositorioHechosImpl implements RepositorioHechosCustom {
  @PersistenceContext
  private EntityManager em;

  @Override
  public List<Hecho> filtrarPorCriterios(List<Criterio> criterios) {
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
    return filtrados;
  }

  @Override
  public long contarHechosPorHora(String horaHH) {
    int hora = Integer.parseInt(horaHH);

    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Long> query = cb.createQuery(Long.class);
    Root<Hecho> root = query.from(Hecho.class);

    // EXTRAER LA HORA DEL LocalDateTime
    Expression<Integer> horaExpr =
            cb.function("hour", Integer.class, root.get("fecha"));

    query.select(cb.count(root))
            .where(cb.equal(horaExpr, hora));

    return em.createQuery(query).getSingleResult();
  }

//  @Override
//  public Optional<String> obtenerHoraConMasHechos(String categoria) {
//
//    CriteriaBuilder cb = em.getCriteriaBuilder();
//    CriteriaQuery<Tuple> query = cb.createTupleQuery();
//    Root<Hecho> root = query.from(Hecho.class);
//
//    // Extraer la hora del DateTime
//    Expression<Integer> horaExpr =
//            cb.function("hour", Integer.class, root.get("fechaHecho"));
//
//    Expression<Long> countExpr = cb.count(root);
//
//    List<Predicate> predicates = new ArrayList<>();
//
//    // Filtro por categoría (opcional)
//    if (categoria != null && !categoria.isBlank()) {
//      predicates.add(cb.equal(root.get("categoria"), categoria));
//    }
//
//    query.multiselect(
//                    horaExpr.alias("hora"),
//                    countExpr.alias("cantidad")
//            )
//            .where(predicates.toArray(new Predicate[0]))
//            .groupBy(horaExpr)
//            .orderBy(cb.desc(countExpr));
//
//    List<Tuple> resultados = em.createQuery(query)
//            .setMaxResults(1)
//            .getResultList();
//
//    if (resultados.isEmpty()) {
//      return Optional.empty();
//    }
//
//    return Optional.of(resultados.get(0).get("hora", Integer.class).toString());
//  }
@Override
public Optional<String> obtenerHoraConMasHechos(String categoria) {
  String sql = "SELECT TOP 1 DATEPART(HOUR, fecha_carga) AS hora, COUNT(*) AS cantidad " +
          "FROM hecho ";

  if (categoria != null && !categoria.isBlank()) {
    sql += "WHERE categoria = :categoria ";
  }

  sql += "GROUP BY DATEPART(HOUR, fecha_carga) " +
          "ORDER BY cantidad DESC";

  Query query = em.createNativeQuery(sql);
  if (categoria != null && !categoria.isBlank()) {
    query.setParameter("categoria", categoria);
  }

  List<Object[]> resultados = query.getResultList();
  if (resultados.isEmpty()) {
    return Optional.empty();
  }

  Integer hora = ((Number) resultados.get(0)[0]).intValue();
  return Optional.of(hora.toString());
}

  @Override
  public Optional<String> obtenerCategoriaConMasHechos() {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Tuple> query = cb.createTupleQuery();
    Root<Hecho> root = query.from(Hecho.class);

    Expression<String> categoriaExpr = root.get("categoria");
    Expression<Long> countExpr = cb.count(root);

    query.multiselect(
                    categoriaExpr.alias("categoria"),
                    countExpr.alias("cantidad")
            )
            .groupBy(categoriaExpr)
            .orderBy(cb.desc(countExpr));

    List<Tuple> resultados = em.createQuery(query)
            .setMaxResults(1)
            .getResultList();

    if (resultados.isEmpty()) {
      return Optional.empty();
    }

    return Optional.of(
            resultados.get(0).get("categoria", String.class)
    );
  }

  @Override
  public List<String> obtenerCategorias() {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Tuple> query = cb.createTupleQuery();
    Root<Hecho> root = query.from(Hecho.class);

    Expression<String> categoriaExpr = root.get("categoria");
    query.multiselect(
                    categoriaExpr.alias("categoria")
            )
            .distinct(true);

    return em.createQuery(query).getResultStream().map(t -> t.get("categoria", String.class)).toList();
  }
}