package domain.controllers;

import domain.business.FuentesDeDatos.FuenteProxy;
import infrastructure.dto.FiltroHechosDTO;
import domain.business.incidencias.Hecho;
import infrastructure.dto.SolicitudEliminacionDTO;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api") // Prefijo para agrupar endpoints
public class HechoController {
  private final FuenteProxy fuenteProxy;

  public HechoController(FuenteProxy fuenteProxy) {
    this.fuenteProxy = fuenteProxy;
  }

  // GET /api/hechos
  @GetMapping("/hechos")
  public List<Hecho> obtenerHechos(@ModelAttribute FiltroHechosDTO filtros) {
    return fuenteProxy.getHechos().stream().filter(filtros::equals).collect(Collectors.toList());
  }

  // GET /api/colecciones/{id}/hechos
  @GetMapping("/colecciones/{handle}/hechos")
  public List<Hecho> obtenerHechosPorColeccion(@PathVariable("handle") String handle, @ModelAttribute FiltroHechosDTO filtros) {
    return fuenteProxy.getHechosDeColeccion();
  }

  // POST /api/solicitudes
  @PostMapping("/solicitudes")
  public void crearSolicitudEliminacion(@RequestBody SolicitudEliminacionDTO dto) {
    fuenteProxy.solicitarEliminacion(dto);
  }
}