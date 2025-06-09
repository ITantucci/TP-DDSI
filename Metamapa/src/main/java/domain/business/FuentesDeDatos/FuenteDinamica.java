package domain.business.FuentesDeDatos;
import domain.business.Usuarios.Perfil;
import domain.business.incidencias.Hecho;
import domain.business.incidencias.Multimedia;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import lombok.Getter;

public class FuenteDinamica extends FuenteDeDatos{

  @Getter
  String nombre;

  @Getter
  LinkedList<Hecho> listaHecho;

  public FuenteDinamica() {
    this.nombre = "Fuente Dinamica";
    this.listaHecho = new LinkedList<>();
  }

  public void agregarHecho(String titulo, String desc, String categoria, Float latitud, Float longitud, LocalDate fechaHecho, Perfil
      autor, Boolean anonimidad, FuenteDeDatos Self, ArrayList< Multimedia > multimedia) {

  }
}