package Estadistica.persistencia;

import Estadistica.business.Estadistica.Estadistica;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.Map;
import java.util.Optional;

public interface RepositorioEstadisticasCustom {
  public <T extends Estadistica> Optional<T> obtenerMasNueva(
          Class<T> clazz,
          Map<String, Object> filtros
  );
}
