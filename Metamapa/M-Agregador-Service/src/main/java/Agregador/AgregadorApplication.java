package Agregador;
import java.util.Collections;
import Agregador.Service.ServiceFuenteDeDatos;
import Agregador.web.ControllerAgregador;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import java.util.concurrent.*;

@SpringBootApplication
public class AgregadorApplication {

  static ServiceFuenteDeDatos serviceFuenteDeDatos = new ServiceFuenteDeDatos(new RestTemplate(),"${fuentes.service.url}");
  static ControllerAgregador controllerAgregador = new ControllerAgregador(serviceFuenteDeDatos);

  private static void scheduleActualizacion()
  {
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    scheduler.scheduleAtFixedRate(() -> controllerAgregador.actualizarHechos(), 0, 2, TimeUnit.HOURS);
  }
  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(AgregadorApplication.class);
    app.setDefaultProperties(Collections.singletonMap("server.port", "server.port"));
    var context = app.run(args);



    scheduleActualizacion();
    // para cerrar la app, comentar cuando se prueben cosas
    //context.close();


  }


  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}