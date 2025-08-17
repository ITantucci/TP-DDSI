package Agregador.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;
import java.util.ArrayList;
import Agregador.business.Hechos.*;
import java.util.HashMap;



@Service
public class ServiceFuenteDeDatos {

  private final RestTemplate restTemplate;
  private final String baseUrl;



  public ServiceFuenteDeDatos(RestTemplate restTemplate,
                              @Value("${fuentes.service.url}") String baseUrl) {
    this.restTemplate = restTemplate;
    this.baseUrl = baseUrl;
  }

  public ArrayList<Map<String,Object>> getHechosDeFuente(int idFuente)
  {
    String url = String.format("%s/%d/hechos", baseUrl, idFuente);
    return restTemplate.getForObject(url,ArrayList.class);
  }

  private Hecho JSONToHecho(Map<String,Object> json)
  {
    String titulo =  json.get("titulo").toString();
    String descripcion = json.get("descripcion").toString();
    String categoria = json.get("categoria").toString();
    Float latitud = Float.parseFloat(json.get("latitud").toString());
    Float longitud = Float.parseFloat(json.get("longitud").toString());
    LocalDate fechaHecho = LocalDate.parse(json.get("fechaHecho").toString());
    LocalDate fechaCarga = LocalDate.parse(json.get("fechaCarga").toString());
    LocalDate fechaModificacion = null;
    Integer fuenteId = Integer.parseInt(json.get("fuenteId").toString());
    Integer usuarioID = Integer.parseInt(json.get("usuarioID").toString());
    Boolean anonimo = Boolean.parseBoolean(json.get("anonimo").toString());
    Boolean eliminado = Boolean.parseBoolean(json.get("eliminado").toString());
    List<Multimedia> multimedia =null;
    HashMap<String,String> metadata=new HashMap<>();

    return new Hecho();
  }

  public ArrayList<Hecho> getHechos()
  {
    String url = String.format("%s/hechos", baseUrl);
    ArrayList<Map<String,Object>> JSONHechos = restTemplate.getForObject(url,ArrayList.class);
    ArrayList<Hecho> hechos = JSONHechos.stream().map(json ->{JSONToHecho(json);}).collect(Collectors.toList());
    return hechos;
  }

}