package Agregador.web;
import Agregador.Service.ServiceAgregador;
import Agregador.business.Consenso.Consenso;
import Agregador.business.Hechos.Hecho;
import java.util.*;
import Agregador.Service.ServiceFuenteDeDatos;
import Agregador.persistencia.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import Agregador.Service.ServiceConsenso;

@RestController
@RequestMapping("/api-agregador")
public class ControllerAgregador {
  private final ServiceFuenteDeDatos servicefuenteDeDatos;
  private final RepositorioAgregador repositorioAgregador;
  private final RepositorioHechos repositorioHechos = new RepositorioHechos();
  private final ServiceAgregador serviceAgregador;
  private final ServiceConsenso serviceConsenso;
  private ArrayList<String> URLsFuentes = new ArrayList<String>();

  public ControllerAgregador(ServiceFuenteDeDatos servicefuenteDeDatos,ServiceConsenso serviceConsenso,
                             RepositorioAgregador repositorioAgregador, ServiceAgregador serviceAgregador) {
    this.servicefuenteDeDatos = servicefuenteDeDatos;
    this.repositorioAgregador = repositorioAgregador;
    this.serviceAgregador = serviceAgregador;
    this.serviceConsenso = serviceConsenso;
  }

  /*
    public void guardarHechos(int idFuente){
      ArrayList<Map<String,Object>> hechos = servicefuenteDeDatos.getHechosDeFuente(idFuente);

      hechos.forEach(h -> repositorioAgregador.persistirHechos(h));
    }
  */
  @PostMapping("/fuenteDeDatos")
  public ResponseEntity<String> agregarFuente(@RequestBody Map<String,Object> body) {
    String url = (String)body.get("url");
    if (url == null) {
      return ResponseEntity.noContent().build();
    }
    URLsFuentes.add(url);
    return ResponseEntity.ok(url);
  }

  @GetMapping("/actualizarHechos")
  public void actualizarHechos() {
    URLsFuentes.forEach(url -> servicefuenteDeDatos.actualizarHechos(url));
  }


  public void consensuarHechos() {
    serviceConsenso.consensuarHechos();
  }

  /*@GetMapping("/")
  public ResponseEntity<Agregador> getAgregador() {
    Agregador agregador = repositorioAgregador.getAgregador();
    if (agregador == null) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(agregador);
  }*/


//  @PostMapping("/api-agregador/fuentes/actualizar")
//  public ResponseEntity<Void> actualizarAgregador() {
//    var fuentes = servicefuenteDeDatos.obtenerFuenteDeDatos();
//    if (fuentes == null || fuentes.isEmpty()) return ResponseEntity.noContent().build();
//    repositorioAgregador.getAgregador().actualizarFuentesDeDatos(fuentes);
//    return ResponseEntity.noContent().build();
//  }

  // esto se va a comunicar con el servicio de colecciones
  // y las colecciones filtran estos hechos
  @GetMapping("/hechos")
  public ResponseEntity<List<Hecho>> getAgregadorHechos() {
    List<Hecho> hechos = new ArrayList<Hecho>(repositorioHechos.findAll());

    if (hechos == null || hechos.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(hechos);
  }
}