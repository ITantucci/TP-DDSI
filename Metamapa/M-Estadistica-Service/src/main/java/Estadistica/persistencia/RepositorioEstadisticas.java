package Estadistica.persistencia;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.repository.JpaRepository;
import Estadistica.business.Estadistica.Estadistica;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositorioEstadisticas extends JpaRepository<Estadistica, Integer>, RepositorioEstadisticasCustom {


}
