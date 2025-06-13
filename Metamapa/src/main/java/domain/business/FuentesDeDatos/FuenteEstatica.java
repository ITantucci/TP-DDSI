package domain.business.FuentesDeDatos;

import domain.business.Parsers.HechoParser;
import domain.business.incidencias.Hecho;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import lombok.Getter;

public class FuenteEstatica extends FuenteDeDatos{

  @Getter
  private String pathCSV;
  @Getter
  private HechoParser parser;

  @Getter
  ArrayList<Hecho> listaHecho;
//TODO: ver bien estas cosas. me quede sin energias, revise hasta Usuarios,tiposSolicitudes(cambiar nombre a solicitudes),Criterios ,incidencias y vi algo de fuentes.

  public void agregarHecho(){throw new UnsupportedOperationException("Not supported yet.");};

  public void cargarCSV(){
    ArrayList<Hecho> hechos = parser.parsearHecho(this.pathCSV);
    for (Hecho h : hechos) {
      h.setFuenteDeDatos(this);
      h.setAutor(null);
      h.setAnonimo(false);
    }
    this.listaHecho = hechos;
  }

}
