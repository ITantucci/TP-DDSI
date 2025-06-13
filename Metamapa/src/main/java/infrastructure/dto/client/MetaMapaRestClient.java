package infrastructure.dto.client;

import infrastructure.dto.HechoDTO;
import infrastructure.dto.SolicitudEliminacionDTO;
import infrastructure.dto.mapper.HechoMapper;
import domain.business.incidencias.Hecho;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.stream.Collectors;

public class MetaMapaRestClient {
  private final String endpointBase;
  private final RestTemplate client;

  public MetaMapaRestClient(String endpointBase, RestTemplate client) {
    this.endpointBase = endpointBase;
    this.client = client;
  }

  public List<Hecho> getHechos(Map<String, String> filtros) {
    String url = buildUrlWithParams(endpointBase + "/hechos", filtros);
    HechoDTO[] respuesta = client.getForObject(url, HechoDTO[].class);
    return Arrays.stream(respuesta).map(HechoMapper::fromDTO).collect(Collectors.toList());
  }

  public List<Hecho> getHechosDeColeccion(String handle, Map<String, String> filtros) {
    String url = buildUrlWithParams(endpointBase + "/colecciones/" + handle + "/hechos", filtros);
    HechoDTO[] respuesta = client.getForObject(url, HechoDTO[].class);
    return Arrays.stream(respuesta).map(HechoMapper::fromDTO).collect(Collectors.toList());
  }

  public void enviarSolicitudEliminacion(SolicitudEliminacionDTO dto) {
    client.postForObject(endpointBase + "/solicitudes", dto, Void.class);
  }

  private String buildUrlWithParams(String baseUrl, Map<String, String> params) {
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl);
    for (Map.Entry<String, String> entry : params.entrySet()) {
      builder.queryParam(entry.getKey(), entry.getValue());
    }
    return builder.toUriString();
  }
}