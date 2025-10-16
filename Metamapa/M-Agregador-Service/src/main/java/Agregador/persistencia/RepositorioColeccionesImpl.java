package Agregador.persistencia;

import Agregador.business.Colecciones.Coleccion;
import Agregador.business.Colecciones.Criterio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
}


