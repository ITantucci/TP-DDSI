package domain.business.FuentesDeDatos;

import domain.business.incidencias.Hecho;
import java.util.LinkedList;
import lombok.Getter;

public class FuenteEstatica extends FuenteDeDatos{

  @Getter
  String nombre;

  @Getter
  LinkedList<Hecho> listaHecho;
//TODO: ver bien estas cosas. me quede sin energias, revise hasta Usuarios,tiposSolicitudes(cambiar nombre a solicitudes),Criterios ,incidencias y vi algo de fuentes.
  //@Getter

}
