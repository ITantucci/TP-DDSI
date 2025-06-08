package domain.business.FuentesDeDatos;

import domain.business.incidencias.Hecho;
//TODO: CAMBIAR A ABSTRACTA EN DIAGRAMA?
public interface FuenteDeDatos {
  //String getNombre();
  //List<Hecho> getHecho();
  abstract void agregarHecho();
}
