package infrastructure.dto;

import domain.business.FuentesDeDatos.FuenteDeDatos;
import domain.business.Usuarios.Perfil;
import domain.business.incidencias.Multimedia;
import domain.business.incidencias.TipoMultimedia;
import domain.business.incidencias.Ubicacion;
import java.util.ArrayList;
import java.util.stream.Collectors;
import lombok.Getter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import lombok.Setter;
import org.javatuples.Pair;

public class HechoDTO {
  @Getter
  private String titulo;
  @Getter
  private String descripcion;
  @Getter
  private String categoria;
  @Getter
  private Ubicacion ubicacion;
  @Getter
  private LocalDate fechaHecho;
  @Getter
  private LocalDate fechaCarga;
  @Getter
  private LocalDate fechaModificacion;
  @Getter
  private FuenteDeDatos fuenteDeDatos;
  @Getter
  private Perfil autor;
  @Getter
  private Boolean anonimo;
  @Getter @Setter
  private Boolean eliminado;
  @Getter
  private ArrayList<Multimedia> multimedia;
  @Getter
  private HashMap<String,String> metadata;


  // Constructores, getters y setters
  public HechoDTO() {}

  public HechoDTO(String titulo, String descripcion, String categoria, Float latitud, Float longitud, LocalDate fechaHecho, FuenteDeDatos fuenteDeDatos, Perfil autor, Boolean anonimo, Boolean eliminado, ArrayList<Pair<TipoMultimedia, String>> tuplaMultimedia) {
    this.titulo = titulo;
    this.descripcion = descripcion;
    this.categoria = categoria;
    this.ubicacion = new Ubicacion(latitud,longitud);
    this.fechaHecho = fechaHecho;
    this.fechaCarga = LocalDate.now();
    this.fechaModificacion = LocalDate.now();
    this.fuenteDeDatos = fuenteDeDatos;
    this.autor = autor;
    this.anonimo = anonimo;
    this.eliminado = eliminado;
    this.multimedia = tuplaMultimedia.stream().map(p -> new Multimedia(p.getValue0(),p.getValue1())).collect(Collectors.toCollection(ArrayList::new));
    this.metadata = new HashMap<>();
  }
}