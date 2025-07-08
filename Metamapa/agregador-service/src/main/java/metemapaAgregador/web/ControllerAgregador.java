package metemapaAgregador.web;


import domain.business.incidencias.Hecho;
import java.util.ArrayList;
import metemapaAgregador.Service.ServiceFuenteDeDatos;
import metemapaAgregador.persistencia.RepositorioAgregador;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-agregador")

public class ControllerAgregador {

  private final ServiceFuenteDeDatos servicefuenteDeDatos;

  public RepositorioAgregador repositorioAgregador = new RepositorioAgregador();

  public ControllerAgregador(ServiceFuenteDeDatos servicefuenteDeDatos) {
    this.servicefuenteDeDatos = servicefuenteDeDatos;
  }

  @GetMapping("/hechos")
  public ArrayList<Hecho> getAgregadorHechos() {
    return repositorioAgregador.agregador.getListaDeHechos();
  }


  @PostMapping ("/fuentesDeDatos/agregar/{idFuente}")
  public void agregarFuente(@PathVariable int idFuente) {
    repositorioAgregador.agregador.agregarFuenteDeDatos(servicefuenteDeDatos.getFuenteDeDatos(idFuente));
  }
  @PostMapping ("/fuentesDeDatos/remover/{idFuente}")
  public void eliminarFuente(@PathVariable int idFuente) {
    repositorioAgregador.agregador.removerFuenteDeDatos(idFuente);
  }
}

/*

@PatchMapping(value = "/solicitudesElimincacion/{id}", consumes = "application/json", produces = "application/json")
  @ResponseBody
  public ResponseEntity actualizarEstadoSolicitud(@PathVariable("id") String id, @RequestBody Map<String, Object> requestBody) {
    try {
      Optional<SolicitudEliminacion> solicitudOpt = solicitudRepository.findById(id);
      if (solicitudOpt.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
      }

*/