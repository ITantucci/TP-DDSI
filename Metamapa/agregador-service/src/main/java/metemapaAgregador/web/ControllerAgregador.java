package metemapaAgregador.web;


import domain.business.Agregador.Agregador;
import domain.business.incidencias.Hecho;
import java.util.ArrayList;

import metemapaAgregador.Service.ServiceFuenteDeDatos;
import metemapaAgregador.persistencia.RepositorioAgregador;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api-agregador")
public class ControllerAgregador {

  private  ServiceFuenteDeDatos servicefuenteDeDatos;

  public ControllerAgregador(ServiceFuenteDeDatos servicefuenteDeDatos){
    this.servicefuenteDeDatos = servicefuenteDeDatos;
  }


  public RepositorioAgregador repositorioAgregador = new RepositorioAgregador();

/*
  public void guardarHechos(int idFuente)
  {
    ArrayList<Map<String,Object>> hechos = servicefuenteDeDatos.getHechosDeFuente(idFuente);

    hechos.forEach(h -> repositorioAgregador.persistirHechos(h));
  }
*/
  public void actualizarHechos()
  {
    /*
    ArrayList<Integer> fuentes = repositorioAgregador.getFuentes();

    fuentes.forEach(f -> guardarHechos(f));
    */
    //TODO este metodo ya lo tiene el agregador
    repositorioAgregador.getAgregador().actualizarHechos();
  }

  @GetMapping("/")
  public Agregador getAgregador() {
    return repositorioAgregador.getAgregador();
  }


  @PostMapping ("/fuentes/actualizar")
  public ResponseEntity<Void> actualizarAgregador() {
    repositorioAgregador.getAgregador().actualizarFuentesDeDatos(servicefuenteDeDatos.obtenerFuenteDeDatos());
    return ResponseEntity.noContent().build();
  }


  //TODO esto se va a comunicar con el servicio de colecciones
  //TODO y las colecciones filtran estos hechos
  @GetMapping("/hechos")
  public ArrayList<Hecho> getAgregadorHechos() {
    return repositorioAgregador.getAgregador().getListaDeHechos();
  }

  @PostMapping ("/fuentesDeDatos/agregar/{idFuente}")
  public void agregarFuente(@PathVariable int idFuente) {
    repositorioAgregador.getAgregador().agregarFuenteDeDatos(servicefuenteDeDatos.getFuenteDeDatos(idFuente));
  }
  @PostMapping ("/fuentesDeDatos/remover/{idFuente}")
  public void eliminarFuente(@PathVariable int idFuente) {
    repositorioAgregador.getAgregador().removerFuenteDeDatos(idFuente);
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