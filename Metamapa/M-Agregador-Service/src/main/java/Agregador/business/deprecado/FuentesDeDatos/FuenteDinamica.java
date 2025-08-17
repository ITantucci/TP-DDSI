package Agregador.business.deprecado.FuentesDeDatos;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.time.LocalDate;
import java.util.ArrayList;
import Agregador.business.deprecado.Usuarios.Usuario;
import Agregador.business.deprecado.incidencias.Hecho;
import Agregador.business.deprecado.incidencias.Multimedia;

@JsonTypeName("FUENTEDINAMICA")
public class FuenteDinamica extends FuenteDeDatos {
  static private Integer contadorID = 1;
  public FuenteDinamica() {
    this.id = contadorID++;
    this.nombre = "Fuente Dinamica";
    this.hechos =  new ArrayList<>();
    this.tipoFuente = tipoFuente.FUENTEDINAMICA;
  }
  public void agregarHecho(Hecho hecho){
    hechos.add(hecho);
  }
  public void agregarHecho(String titulo, String desc, String categoria, Float latitud, Float longitud, LocalDate fechaHecho,
                           Perfil autor, Boolean anonimidad, ArrayList<Multimedia> multimedia) {
    this.hechos.add(new Hecho(titulo,desc,categoria,latitud,longitud,fechaHecho,autor,this.id ,anonimidad,multimedia));
  }
}