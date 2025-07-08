package Metamapa.service;

import domain.business.FuentesDeDatos.FuenteDeDatos;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ServiceFuenteDeDatos {

  private final RestTemplate restTemplate;
  private final String baseUrl;

  public ServiceFuenteDeDatos(RestTemplate restTemplate,
                             @Value("${fuentes.service.url}") String baseUrl) {
    this.restTemplate = restTemplate;
    this.baseUrl = baseUrl;
  }

  public FuenteDeDatos getFuenteDeDatos(Integer idFuente) {
    String url = String.format("%s/fuentesDeDatos/%d", baseUrl, idFuente);
    return restTemplate.getForObject(url, FuenteDeDatos.class);
  }
  public void cargarCSV(Integer idFuente,MultipartFile file){
    

    String url = String.format("%s/api-fuentesDeDatos/%d/cargarCSV", baseUrl, idFuente);
    restTemplate.postForObject(url, null, Void.class);
  }



  public void agregarFuente(Integer idFuente){
    String url = String.format("%s/api-agregador/fuentesDeDatos/agregar/%d", baseUrl, idFuente);
    restTemplate.postForObject(url, null, Void.class);
  }
}