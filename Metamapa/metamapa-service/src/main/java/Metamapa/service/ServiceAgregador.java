package Metamapa.Service;
import DTO.AgregadorDTO;
import DTO.HechoDTO;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ServiceAgregador {

  private final RestTemplate restTemplate;
  private final String baseUrl;

  public ServiceAgregador(RestTemplate restTemplate,
                         @Value("${agregador.service.url}") String baseUrl) {
    this.restTemplate = restTemplate;
    this.baseUrl = baseUrl;
  }
//TODO esta no se va a usar(solo la deberia usar las colecciones), solo para pruebas
  public ArrayList<HechoDTO> getAgregadorHechos() {
    String url = String.format("%s/api-agregador/hechos", baseUrl);
    return restTemplate.getForObject(url, ArrayList.class);
  }

  public AgregadorDTO getAgregador() {
    String url = String.format("%s/api-agregador/", baseUrl);
    return restTemplate.getForObject(url, AgregadorDTO.class);
  }

  public void agregarFuente(Integer idFuente){
    String url = String.format("%s/api-agregador/fuentesDeDatos/agregar/%d", baseUrl, idFuente);
    restTemplate.postForObject(url, null, Void.class);
  }
  public void removerFuente(Integer idFuente){
    String url = String.format("%s/api-agregador/fuentesDeDatos/remover/%d", baseUrl, idFuente);
    restTemplate.postForObject(url, null, Void.class);
  }
}