/*
package Estadistica.Service;
import Estadistica.business.Estadistica.*;
import Estadistica.persistencia.*;
import Estadistica.web.ControllerEstadistica;
import io.micrometer.core.instrument.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.time.LocalTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TareasProgramadas {
     private final ControllerEstadistica controllerEstadistica;
     private final RepositorioHechos repositorioHechos;
     private final MeterRegistry registry;
     private final RestTemplate restTemplate = new RestTemplate();
  //descomentar si hacemos BI
//     private Hecho JsonToHecho(Map<String, Object> json,String ColeccionId) {
//       Optional<Hecho> hechoExistente = repositorioHechos.findById((BigInteger) json.get("Id"));
//       Hecho hecho = hechoExistente.orElse(
//               new Hecho((BigInteger) json.get("id"),
//                       new Provincia((String)json.get("provincia")),
//                       new Categoria((String)json.get("categoria"))
//                       ,new ArrayList<>(),
//                       ((List<Boolean>)json.get("solicitudesEliminacion")).stream().map(s -> new SolicitudEliminacion(s)).toList(),
//                       new Hora((LocalTime)json.get("hora"))));
//      hecho.addColeccion(new Coleccion(ColeccionId));
//      return hecho;
//     }
//
//     void guardarHechosPorColeccion(List<Map<String, Object>> raw,String UrlBase)
//     {
//       for (Map<String, Object> jsonColeccion : raw) {
//           String coleccionId = (String)jsonColeccion.get("id");
//           String urlColeccion = UrlBase + "/" + coleccionId + "/hechos";
//           ResponseEntity<Map> response = restTemplate.getForEntity(urlColeccion, Map.class);
//           if (response.getStatusCode() == HttpStatus.OK) {
//             List<Map<String, Object>> hechosRaw = (List<Map<String, Object>>)response.getBody();
//             List<Hecho> hechos = hechosRaw.stream()
//                     .map(json -> JsonToHecho(json,coleccionId))
//                     .toList();
//             repositorioHechos.saveAll(hechos);
//           }
//       }
//     }
     public void actualizarEstadisticas(String UrlBase,String UrlSolEliminacion,String UrlColecciones) {
//       //Hechos
//       ResponseEntity<List<Map<String, Object>>> resp = restTemplate.exchange(
//               UrlColecciones, HttpMethod.GET, null, new ParameterizedTypeReference<>() {}
//       );
//       List<Map<String, Object>> raw = Optional.ofNullable(resp.getBody()).orElseGet(List::of);
//
//       guardarHechosPorColeccion(raw,UrlBase);
     }


     @Scheduled(fixedRate = 30 * 60 * 1000) //TODO: revisar tiempo
     public void actualizarEstadisticaUI() {
       var counter = registry.find("http.server.requests.count").counter();
       double requests = (counter != null) ? counter.count() : 0.0;
       if (requests < 100) {
         controllerEstadistica.actualizarEstadisticaUI();
       }
     }
}*/
