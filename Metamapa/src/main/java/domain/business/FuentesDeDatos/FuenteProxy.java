package domain.business.FuentesDeDatos;

import domain.business.Parsers.HechoParser;
import lombok.Getter;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import domain.business.incidencias.Hecho;
//import infrastructure.dto.client.MetaMapaRestClient;
import domain.business.criterio.Criterio;
import domain.business.criterio.Coleccion;
import java.time.LocalDate;

public class FuenteProxy extends FuenteDeDatos {
    @Getter
    private String endpointBase;
    @Getter
    private HechoParser parser;

    public FuenteProxy(String endpointBase, HechoParser parser) {
        this.endpointBase = endpointBase;
        this.parser = parser;
    }

    public void agregarHecho() {
    }

    public void getHechosDeColeccion() {

    }

    public void solicitarEliminacion() {
    }

}