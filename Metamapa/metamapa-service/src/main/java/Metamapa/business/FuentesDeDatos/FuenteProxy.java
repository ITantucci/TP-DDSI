package Metamapa.business.FuentesDeDatos;
import Metamapa.business.Parsers.HechoParser;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.Setter;
//import infrastructure.dto.client.MetaMapaRestClient;

@JsonTypeName("FUENTEPROXY")
public abstract class FuenteProxy extends FuenteDeDatos {
    @Getter
    public String endpointBase;
    @Getter @Setter
    public HechoParser parser;
    static private Integer contadorID = 1;
    public FuenteProxy(String nombre,String endpointBase) {
        this.nombre = nombre;
        this.endpointBase = endpointBase;
        this.id = contadorID++;
        this.tipoFuente = tipoFuente.FUENTEPROXY;
    }

    public FuenteProxy() {}
    /*public void actualizarHechos() {
    }*/
    /*public void getHechosDeColeccion() {
    }
    public void solicitarEliminacion() {
    }*/
}