package FuenteDemo.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ServiceAgregador {
  private RestTemplate restTemplate;
  private String baseUrl;

  public ServiceAgregador(RestTemplate restTemplate, @Value("${M.Agregador.Service.url}") String baseUrl) {
    this.restTemplate = restTemplate;
    this.baseUrl = baseUrl;
  }

  public void publicarmeAAgregador() {
    String url = String.format("%s/fuenteDeDatos", "${M.Agregador.Service.url}");
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    String body = """
        {
            "URLBase": """ + "${M.FuenteProxy.Service.url}" + """
        }
    """;

    HttpEntity<String> request = new HttpEntity<>(body, headers);
    try {
      restTemplate.postForObject(url, request, String.class);
      System.out.println("✅ Publicado exitosamente en agregador: " + url);
    } catch (Exception e) {
      System.err.println("⚠️ No se pudo conectar al agregador en " + url);
      System.err.println("   → Error: " + e.getMessage());
    }  }
}