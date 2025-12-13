package Estadistica.persistencia;

import Estadistica.business.Solicitudes.SolicitudEliminacion;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface RepositorioSolicitudesEliminacion extends JpaRepository<SolicitudEliminacion, Integer> {
  List<SolicitudEliminacion> findById(int id);

  @Query("SELECT s FROM SolicitudEliminacion s WHERE s.id <> :id")
  List<SolicitudEliminacion> findAllWhereIdNot(@Param("id") int estado);

  // Todas las solicitudes con estado SPAM
  List<SolicitudEliminacion> findByEstado(String estado);

  // Cantidad de solicitudes con estado SPAM
  long countByEstado(String estado);
}