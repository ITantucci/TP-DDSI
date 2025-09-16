package Estadistica.web;

import Estadistica.Service.ServiceEstadistica;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Tag(name = "Servicio de Estadísticas", description = "Consultas y exportación de estadísticas")
@RequestMapping("/estadistica")
public class ControllerEstadistica {
  private final ServiceEstadistica estadisticaService;
  //TODO: tendra repositorio? no dice nada la consigna

  public ControllerEstadistica(ServiceEstadistica estadisticaService) {
    this.estadisticaService = estadisticaService;
  }

  @PostMapping("/actualizar")
  public ResponseEntity<Void> actualizarEstadisticas() {
    estadisticaService.actualizar();
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/actualizar-ui")
  public ResponseEntity<Void> actualizarEstadisticaUI() {
    estadisticaService.actualizarDashboards();
    return ResponseEntity.accepted().build();
  }
  //Se elimina creo
  public void generarEstadisticas() {}

  @Operation(summary = "Exportación CSV genérica")
  @ApiResponse(responseCode = "200", description = "CSV",
          content = @Content(mediaType = "text/csv"))
  @GetMapping(value = "/export", produces = "text/csv")
  public void exportarDatos(@RequestParam String tipo){

  }

}
