package DTO;

import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.ArrayList;
import org.springframework.web.client.RestTemplate;



@JsonTypeName("FUENTEMETAMAPA")
public class FuenteMetamapaDTO extends FuenteProxyDTO {
  final private RestTemplate restTemplate;
  //TODO agregar el id al hecho
  public FuenteMetamapaDTO(String nombre, String endpointBase) {
    super(nombre,endpointBase);
    this.id = contadorID++;
    this.nombre = nombre;
    this.hechos = new ArrayList<>();
    this.restTemplate = new RestTemplate();
    this.tipoFuente = tipoFuente.FUENTEMETAMAPA;
  }
}