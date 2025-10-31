package Estadistica.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate; // O WebClient/HttpClient
import com.fasterxml.jackson.databind.*;

@Service
public class GeocodingService {

  private final RestTemplate restTemplate = new RestTemplate();
  private final ObjectMapper mapper = new ObjectMapper();

  // API pública de Nominatim (OpenStreetMap)
  private static final String NOMINATIM_URL =
      "https://nominatim.openstreetmap.org/reverse?format=json&lat={lat}&lon={lon}&zoom=10&addressdetails=1";

  public String obtenerProvincia(double latitud, double longitud) {
    try {

      String resultJson = restTemplate.getForObject(
          NOMINATIM_URL,
          String.class,
          latitud,
          longitud
      );

      JsonNode root = mapper.readTree(resultJson);

      JsonNode address = root.path("address");

      if (address.has("state")) {
        return address.get("state").asText();
      }

      if (address.has("state_district")) {
        return address.get("state_district").asText();
      }

      return "Provincia Desconocida";

    } catch (Exception e) {
      System.err.println("Error en la geocodificación inversa: " + e.getMessage());
      return "Error de GeoAPI";
    }
  }
}