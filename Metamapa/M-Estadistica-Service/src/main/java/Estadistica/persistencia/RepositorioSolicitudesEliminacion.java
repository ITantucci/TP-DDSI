package Estadistica.persistencia;

import Estadistica.business.Solicitudes.Solicitud;
import Estadistica.business.Solicitudes.SolicitudEliminacion;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface RepositorioSolicitudesEliminacion extends JpaRepository<SolicitudEliminacion, Integer> {

  @Query("""
  SELECT se
  FROM SolicitudEliminacion se
  JOIN Solicitud s ON s.id = se.id
  WHERE s.estado = :estado
""")
  List<SolicitudEliminacion> findAllWhereEstadoIs(@Param("estado") String estado);

}