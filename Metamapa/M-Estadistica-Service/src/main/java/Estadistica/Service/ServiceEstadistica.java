package Estadistica.Service;

import Estadistica.DTO.EstadisticaDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ServiceEstadistica {
    private final RestTemplate restTemplate;
    private final String baseUrl;

    public ServiceEstadistica(RestTemplate restTemplate,
                            @Value("${M.Agregador.Service.url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public void actualizar(){

    }

    public void actualizarDashboards(){

    }

    public String obtenerCategoriaMasReportada(){
        String url = baseUrl + "/api-hechos/categoria-mas-reportada";
        return restTemplate.getForObject(url, String.class);
    }

    public long getSolicitudesSpam() {
        String url = baseUrl + "/api-solicitudes/solicitudesEliminacion?spam=true";
        List<?> lista = restTemplate.getForObject(url, List.class);
        return (lista == null) ? 0L : lista.size();
    }

    public void exportarCsv(){

    }

    public EstadisticaDTO obtenerResumen() {
        EstadisticaDTO dto = new EstadisticaDTO();

        //TODO: falta resto de cosas
        // 3) Otros campos aún no implementados (los podés dejar en null o 0)
        dto.setProvinciaMasReportada(null);
        dto.setCantidadProvinciaMasReportada(0);
        dto.setProvinciaPorCategoria(null);
        dto.setCantidadProvinciaPorCategoria(0);
        dto.setHechosPorHoraCategoria(null);
        dto.setTotalHechos(0);

        //Categoría más reportada
        String topCategoria = obtenerCategoriaMasReportada();
        dto.setCategoriaMasReportada(topCategoria);

        dto.setCantidadCategoriaMasReportada(0);

        // 2) Cantidad de solicitudes de eliminación que son spam
        long spamCount = getSolicitudesSpam();
        dto.setCantidadSolicitudesSpam(spamCount);

        return dto;
    }

}
