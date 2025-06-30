package controllers;

import domain.Persistencia.RepositorioFuentes;
import domain.business.FuentesDeDatos.FuenteDeDatos;
import java.util.Collections;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@SpringBootApplication
@Controller

public class ControllerFuentesDeDatos {
  public RepositorioFuentes repositorioFuentes = new RepositorioFuentes();
  public static void main(String[] args) {

    //SpringApplication.run(testApplication.class, args);
    SpringApplication app = new SpringApplication(controllers.ControllerFuentesDeDatos.class);
    app.setDefaultProperties(Collections.singletonMap("server.port", "9001"));
//    app.setDefaultProperties(Collections.singletonMap("server.address", "192.168.0.169"));
    var context = app.run(args);
    // para cerrar la app, comentar cuando se prueben cosas
    context.close();
  }

  @GetMapping("/fuentesDeDatos/{idFuenteDeDatos}/hechos")
  public String  getFuenteDeDatos(
      @PathVariable(value = "idFuenteDeDatos") Integer idfuenteDeDatos,
      Model model) {
    FuenteDeDatos fuente = repositorioFuentes.buscarFuente(idfuenteDeDatos);

    model.addAttribute("fuente", fuente);
    return "fuenteDeDatos";
  }

  }
