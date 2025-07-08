package Metamapa.web;


import Metamapa.service.ServiceFuenteDeDatos;
import Metamapa.service.ServiceAgregador;
import domain.business.incidencias.Hecho;
import java.util.ArrayList;
import java.util.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ControllerMetamapa {

  private final ServiceFuenteDeDatos serviceFuenteDeDatos;
  private final ServiceAgregador serviceAgregador;

  public ControllerMetamapa(ServiceFuenteDeDatos serviceFuenteDeDatos,
                            ServiceAgregador serviceAgregador) {
    this.serviceFuenteDeDatos = serviceFuenteDeDatos;
    this.serviceAgregador = serviceAgregador;
  }

  @GetMapping("/metamapa/fuentesDeDatos/{id}")
  public String mostrarFuente(@PathVariable("id") Integer id, Model model) {
    model.addAttribute("fuente", serviceFuenteDeDatos.getFuenteDeDatos(id));
    return "fuenteDeDatos";
  }


  //TODO No necesitamos conectarnos con el agregador
  @GetMapping("/metamapa/agregador/hechos")
  public String mostrarAgregador(Model model) {
    model.addAttribute("hechos", serviceAgregador.getAgregadorHechos());
    return "agregador";
  }

  @PostMapping("/metamapa/agregador/fuentesDeDatos/agregar/{idFuente}")
  public void agregarFuente(@PathVariable("idFuente") Integer idFuente)
  {
    serviceAgregador.agregarFuente(idFuente);
  }
  @PostMapping("/metamapa/agregador/fuentesDeDatos/remover/{idFuente}")
  public void removerFuente(@PathVariable("idFuente") Integer idFuente)
  {
    serviceAgregador.removerFuente(idFuente);
  }
  @PostMapping("/metamapa/fuentesDeDatos/{idFuenteDeDatos}/cargarCSV")
  public void cargarCSV(@PathVariable("idFuenteDeDatos") Integer idFuente,@RequestParam("file") MultipartFile file)
  {
    serviceFuenteDeDatos.cargarCSV(idFuente,file);
  }

  //@GetMapping ("/metamapa/colecciones/{id}/hechos")
  //public String mostrarColeccion(@PathVariable("handler")UUID handler,)


  @GetMapping("/")
  public String redirectRoot() {
    return "redirect:/metamapa";
  }

  @GetMapping("/metamapa")
  public String mostrarHome(Model model) {
    return "home";
  }

  @GetMapping("/metamapa/consultas")
  public String mostrarConsultas(Model model) {
    return "consultas";
  }
  //API
  @GetMapping("/metamapa/api/colecciones/{idColeccion}/hechos")
  public ArrayList<Hecho> consultarHechos (@PathVariable ("idColeccion")Integer id) {
  return new ArrayList<>();
    //return serviceColecciones.getColeccion(idColeccion);
  }
}
