package domain.business.FuentesDeDatos;

import domain.business.Parsers.HechoParser;
import lombok.Getter;

public class FuenteProxy extends FuenteDeDatos{
    @Getter
    private String endpointBase;
    @Getter
    private HechoParser parser;

    public void agregarHecho(){}
    public void getHechosDeCollecion(){}
    public void solicitarEliminacion(){}
}
