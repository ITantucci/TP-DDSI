package domain.business.FuentesDeDatos;

import domain.business.Parsers.HechoParser;
import domain.business.incidencias.Hecho;
import java.util.LinkedList;
import lombok.Getter;

public class FuenteEstatica extends FuenteDeDatos{

  @Getter
  private String nombre;
  @Getter
  private String pathCSV;
  @Getter
  private HechoParser parser;

  @Getter
  LinkedList<Hecho> listaHecho;
//TODO: ver bien estas cosas. me quede sin energias, revise hasta Usuarios,tiposSolicitudes(cambiar nombre a solicitudes),Criterios ,incidencias y vi algo de fuentes.
  //@Getter
  public void agregarHecho(){throw new UnsupportedOperationException("Not supported yet.");};
}
