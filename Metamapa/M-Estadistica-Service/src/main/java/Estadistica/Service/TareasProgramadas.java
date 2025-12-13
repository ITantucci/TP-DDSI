/*
package Estadistica.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TareasProgramadas {
     private final ServiceEstadistica serviceEstadistica;

  @Scheduled(fixedRate = 30 * 60 * 1000) // cada 30 min
  public void recalcularEstadisticas() {
    serviceEstadistica.actualizar();           // refresca datos del agregador
    serviceEstadistica.actualizarDashboards(); // recalcula stats y actualiza lo que consume la UI
  }
}*/
