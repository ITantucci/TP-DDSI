package domain.business.FuentesDeDatos;

import domain.business.Usuarios.Perfil;
import domain.business.incidencias.Hecho;
import domain.business.incidencias.Multimedia;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import lombok.Getter;

//TODO: CAMBIAR A ABSTRACTA EN DIAGRAMA?
abstract class FuenteDeDatos {

  @Getter
  String nombre;

  @Getter
  LinkedList<Hecho> listaHecho;

  void agregarHecho();
}