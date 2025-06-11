package domain.business.FuentesDeDatos;

import domain.business.Parsers.HechoParser;
import domain.business.criterio.CriterioUbicacion;
import infrastructure.dto.SolicitudEliminacionDTO;
import lombok.Getter;
import infrastructure.client.MetaMapaRestClient;
import domain.business.incidencias.Hecho;
import infrastructure.dto.FiltroHechosDTO;
import domain.business.criterio.Criterio;
import domain.business.criterio.Coleccion;
import domain.business.criterio.CriterioCategoria;


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

    public void agregarHecho(){}

    public List<Hecho> getHechos(FiltroHechosDTO filtros) {
        // Obtener todos los hechos desde la fuente externa (dummy o real)
        List<Hecho> hechos = List.of(
                new Hecho("id1", "Descripción 1", "A", 0.0f, 0.0f, LocalDate.now()),
                new Hecho("id2", "Descripción 2", "B", 0.0f, 0.0f, LocalDate.now())
        );

        // Aplicar los criterios generados por el DTO
        List<Criterio> criterios = filtros.aCriterios();
        return hechos.stream()
                .filter(h -> criterios.stream().allMatch(c -> c.cumple(h)))
                .toList();
    }

    public List<Hecho> getHechosDeColeccion(String handle, FiltroHechosDTO filtros) {
        //TODO: revisar Por ahora asi
        Coleccion coleccion = new Coleccion();

        return coleccion.filtrarPorCriterios(filtros.aCriterios());
    }


    public void solicitarEliminacion(SolicitudEliminacionDTO dto) {
        client.enviarSolicitudEliminacion(dto);
        //Da error 401, el resto no
        //System.out.println("Solicitud recibida: " + dto);
    }
}
