/* package infrastructure.dto.config;

import domain.business.FuentesDeDatos.FuenteProxy;
import domain.business.Parsers.HechoParser;
import domain.business.incidencias.Hecho;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class FuenteProxyConfig {

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Bean
  public HechoParser hechoParser() {
    return new HechoParser() {
      @Override
      public List<Hecho> parsearHecho(String path) {
        return List.of();
      }
    };
  }

  @Bean
  public FuenteProxy fuenteProxy() {
    String endpointBase = "http://localhost:8080"; // o el endpoint real de la otra instancia
    return new FuenteProxy(endpointBase, hechoParser());
  }
}
*/