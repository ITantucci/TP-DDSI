package Metamapa.service;
import Metamapa.business.Colecciones.Coleccion;
import Metamapa.business.Colecciones.Criterio;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import Metamapa.business.Consenso.Consenso;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ServiceColecciones {

  private final RestTemplate restTemplate;
  private final String baseUrl;

  public ServiceColecciones(RestTemplate restTemplate,
                            @Value("${colecciones.service.url}") String baseUrl) {
    this.restTemplate = restTemplate;
    this.baseUrl = baseUrl;
  }

  public ArrayList<Coleccion> getColecciones() {
    String url = String.format("%s/api-colecciones/", baseUrl);
    return restTemplate.getForObject(url, ArrayList.class);
  }

  public Coleccion getColeccion(UUID uuid) {
    String url = String.format("%s/api-colecciones/%s", baseUrl, uuid);
    return restTemplate.getForObject(url, Coleccion.class);
  }
/*
  public Coleccion(String titulo, String desc, ArrayList<Criterio> pertenencia, ArrayList<Criterio> noPertenencia){
    this.titulo=titulo;
    this.descripcion = desc;
    this.criterioPertenencia = pertenencia;
    this.criterioNoPertenencia = noPertenencia;
    //this.agregador=agregador;
    this.handle = UUID.randomUUID();
  }
*/
  public UUID crearColeccion(String titulo,String descripcion, ArrayList<Criterio> pertenencia, ArrayList<Criterio>noPertenencia ) {
    Map<String, Object> payload = new HashMap<>();
    payload.put("titulo", titulo);
    payload.put("descripcion", descripcion);
    payload.put("pertenencia", pertenencia);
    payload.put("noPertenencia", noPertenencia);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<Map<String,Object>> request = new HttpEntity<>(payload, headers);

    @SuppressWarnings("unchecked")
    Map<String,Object> response = restTemplate.postForObject(
        baseUrl + "/api-colecciones/",
        request,
        Map.class
    );
    UUID handle = (UUID) response.get("handle");
    return handle;
  }

  public boolean actualizarAlgoritmoConsenso(UUID idColeccion, String algoritmo) {
    String url = String.format("%s/api-colecciones/%s/consenso/%s", baseUrl, idColeccion, algoritmo);
    ResponseEntity<Void> resp = restTemplate.exchange(url, HttpMethod.PATCH, HttpEntity.EMPTY, Void.class);
    return resp.getStatusCode().is2xxSuccessful();
  }

}