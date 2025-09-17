package Estadistica.web;

import Estadistica.DTO.EstadisticaDTO;
import Estadistica.Service.ServiceEstadistica;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/estadistica")
public class ControllerEstadisticaUI {
  private final ServiceEstadistica estadisticaService;

  public ControllerEstadisticaUI(ServiceEstadistica estadisticaService) {
    this.estadisticaService = estadisticaService;
  }

  @GetMapping("/dashboard")
  public String dashboard(Model model) {
    // Cargamos un primer “snapshot” para que la página no aparezca vacía
    EstadisticaDTO snap = estadisticaService.obtenerResumen();
    model.addAttribute("snapshot", snap);
    return "dashboard"; // -> templates/dashboard.html
  }

}