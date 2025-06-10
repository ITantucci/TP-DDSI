package infrastructure.controller;

import domain.business.FuentesDeDatos.FuenteProxy;
import domain.business.incidencias.Hecho;
import infrastructure.dto.SolicitudEliminacionDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api") // Prefijo para agrupar tus endpoints
public class HechoController {
    private final FuenteProxy fuenteProxy;

    public HechoController(FuenteProxy fuenteProxy) {
        this.fuenteProxy = fuenteProxy;
    }

    // GET /api/hechos
    @GetMapping("/hechos")
    public List<Hecho> obtenerHechos() {
        return fuenteProxy.getHechos();
    }

    // GET /api/colecciones/{id}/hechos
    @GetMapping("/colecciones/{id}/hechos")
    public List<Hecho> obtenerHechosPorColeccion(@PathVariable("id") String id) {
        return fuenteProxy.getHechosDeColeccion(id);
    }

    // POST /api/solicitudes
    @PostMapping("/solicitudes")
    public void crearSolicitudEliminacion(@RequestBody SolicitudEliminacionDTO dto) {
        fuenteProxy.solicitarEliminacion(dto);
    }
}
