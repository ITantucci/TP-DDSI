package FuenteDemo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class FuenteDemoApplication {
  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(FuenteDemoApplication.class);
    //app.setDefaultProperties(Collections.singletonMap("server.port", "9006"));
    var context = app.run(args);
    // para cerrar la app, comentar cuando se prueben cosas
    //context.close();
  }
  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}