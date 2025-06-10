package domain.business.FuentesDeDatos;

import domain.business.Parsers.HechoParser;
import infrastructure.dto.SolicitudEliminacionDTO;
import lombok.Getter;
import infrastructure.client.MetaMapaRestClient;
import domain.business.incidencias.Hecho;

import java.util.HashMap;
import java.util.List;

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
        return client.getHechos(new HashMap<>());
    }

    public List<Hecho> getHechosDeColeccion(String handle) {
        return client.getHechosDeColeccion(handle, new HashMap<>());
    }

    public void solicitarEliminacion(SolicitudEliminacionDTO dto) {
        client.enviarSolicitudEliminacion(dto);
    }
}
