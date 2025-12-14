package Agregador.persistencia;

import Agregador.business.Colecciones.*;
import jakarta.persistence.*;
import java.util.*;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioColeccionesImpl implements RepositorioColeccionesCustom {
  @PersistenceContext
  private EntityManager em;

  @Override
  public Optional<Coleccion> getColeccion(UUID id) {
    try {
      Coleccion coleccion = em.createQuery("""
                          SELECT DISTINCT c
                          FROM Coleccion c
                          LEFT JOIN FETCH c.criterios
                          LEFT JOIN FETCH c.consenso
                          WHERE c.handle = :id
                      """, Coleccion.class)
              .setParameter("id", id)
              .getSingleResult();
      return Optional.of(coleccion);
    } catch (NoResultException e) {
      return Optional.empty();
    }
  }

  @Override
  public List<Criterio> getCriteriosColeccion(UUID id) {
    // Buscar la colección con sus criterios persistidos
    Coleccion coleccion = em.createQuery("""
                        SELECT c FROM Coleccion c 
                        LEFT JOIN FETCH c.criterios 
                        WHERE c.handle = :id
                    """, Coleccion.class)
            .setParameter("id", id)
            .getSingleResult();
    // Construir lista de criterios (base + filtros)
    List<Criterio> criterios = new ArrayList<>();
    criterios.addAll(coleccion.getCriterios());
    return criterios;
  }
  /*@Override
  public List<Coleccion> buscarTextoLibre(String query) {
    String q = (query == null) ? "" : query.trim();
    if (q.isEmpty()) return List.of();

    // 1) Intento Full-Text (SQL Server)
    try {
      String contains = toContainsQuery(q);

      // IMPORTANTE: nombre real de tabla/columnas (ajustar si cambian)
      String sql = """
          SELECT * FROM Coleccion c
          WHERE CONTAINS((c.titulo, c.descripcion), :q)
          """;

      return em.createNativeQuery(sql, Coleccion.class)
              .setParameter("q", contains)
              .getResultList();

    } catch (Exception ex) {
      // 2) Fallback LIKE (por si no hay FT index o falla sintaxis)
      String sqlLike = """
          SELECT * FROM Coleccion c
          WHERE LOWER(c.titulo) LIKE LOWER(CONCAT('%', :q, '%'))
             OR LOWER(c.descripcion) LIKE LOWER(CONCAT('%', :q, '%'))
          """;

      return em.createNativeQuery(sqlLike, Coleccion.class)
              .setParameter("q", q)
              .getResultList();
    }
  }
  private String toContainsQuery(String raw) {
    String[] tokens = raw.trim().split("\\s+");
    List<String> parts = new ArrayList<>();

    for (String t : tokens) {
      String token = t.replace("\"", "");
      if (token.length() < 2) continue;
      parts.add("\"" + token + "*\"");
    }

    // si no hay tokens “útiles”, buscá la frase completa
    if (parts.isEmpty()) {
      return "\"" + raw.replace("\"", "") + "\"";
    }

    return String.join(" AND ", parts);
  }*/
}