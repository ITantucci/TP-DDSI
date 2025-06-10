package domain.business.FuentesDeDatos;

import domain.business.Parsers.HechoParser;
import infrastructure.dto.SolicitudEliminacionDTO;
import lombok.Getter;
import infrastructure.client.MetaMapaRestClient;
import domain.business.incidencias.Hecho;

import java.util.HashMap;
import java.util.List;
import java.time.LocalDate;

import org.springframework.web.client.RestTemplate;


public class FuenteProxy extends FuenteDeDatos {
    @Getter
    private String endpointBase;
    @Getter
    private HechoParser parser;
    private final MetaMapaRestClient client;

    public FuenteProxy(String endpointBase, HechoParser parser, RestTemplate restTemplate) {
        this.endpointBase = endpointBase;
        this.parser = parser;
        this.client = new MetaMapaRestClient(endpointBase, restTemplate);
    }

    public List<Hecho> getHechos() {
        //return client.getHechos(new HashMap<>());
        // Temporalmente devolver lista dummy
        return List.of(
                new Hecho("id1", "Descripción 1", "Categoría A", 0.0f, 0.0f, LocalDate.now()),
                new Hecho("id2", "Descripción 2", "Categoría B", 0.0f, 0.0f, LocalDate.now())
        );

    }

    public List<Hecho> getHechosDeColeccion(String handle) {
        //return client.getHechosDeColeccion(handle, new HashMap<>());
        // Temporalmente devolver lista dummy
        return List.of(
                new Hecho("h1", "Hecho de colección", "Cat", 0.0f, 0.0f, LocalDate.now())
        );

    }

    public void solicitarEliminacion(SolicitudEliminacionDTO dto) {
        client.enviarSolicitudEliminacion(dto);
        //Da error 401, el resto no
        //System.out.println("Solicitud recibida: " + dto);
    }
}
