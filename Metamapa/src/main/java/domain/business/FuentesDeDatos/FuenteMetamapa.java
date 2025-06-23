package domain.business.FuentesDeDatos;
import domain.business.Parsers.HechoParser;
import domain.business.externo.demo.Conexion;
import domain.business.incidencias.Hecho;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

public class FuenteMetamapa extends FuenteProxy{
  private RestTemplate restTemplate;

  public FuenteMetamapa(String nombreFuente, URL endpointBase, HechoParser parser) {
    super(endpointBase, parser);
    this.nombre = nombreFuente;
    this.hechos = new ArrayList<>();
    this.restTemplate = new RestTemplate();
  }
  /*public void actualizarHechos() {
    List<Hecho> hechosNuevos = parser.parsearHecho(url + "/hechos");
    hechos.clear(); // opcional: podés evitarlo si querés acumular
    hechos.addAll(hechosNuevos);

    esto creo que seria: String url = getEndpointBase().toString() + "/hechos";

  }*/

  /*public void solicitarEliminacion(Hecho hecho) {
    HttpService.post(endpoint + "/solicitudes", hecho.toSolicitudJson());
  }*/
}