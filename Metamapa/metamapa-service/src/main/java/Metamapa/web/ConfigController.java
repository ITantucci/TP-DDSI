package Metamapa.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController {

  // Lee el valor desde application.properties
  @Value("${M.Agregador.Service.url}")
  private String agregadorUrl;

  @GetMapping(value = "/config.js", produces = "application/javascript")
  public String configJs() {
    String base = agregadorUrl.endsWith("/") ?
        agregadorUrl.substring(0, agregadorUrl.length() - 1) : agregadorUrl;

    return """
        window.METAMAPA = {
            API_AGREGADOR: '%s/api-agregador',
            API_HECHOS: '%s/api-hechos',
            API_COLECCIONES: '%s/api-colecciones',
            API_SOLICITUDES: '%s/api-solicitudes',
            API_ESTADISTICAS: '%s/api-estadisticas'
        };
        """.formatted(base, base, base, base,base);
  }
}