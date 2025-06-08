package domain.business.incidencias;


import domain.business.FuentesDeDatos.FuenteDeDatos;
import domain.business.Usuarios.Perfil;
import domain.business.tiposSolicitudes.SolicitudEdicion;
import java.util.LinkedList;
import java.util.List;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

public class Hecho {
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
  private final LocalDate fechaCarga;
  @Getter
  private LocalDate fechaModificacion;
  @Getter
  private FuenteDeDatos fuenteDeDatos; //porque estaba comentada? TODO:Agregar en el constructor
  @Getter
  private Perfil autor;
  @Getter
  private Boolean anonimo;
  @Getter
  private Boolean eliminado;
  @Getter
  private List<Multimedia> multimedia;
  private List<String> metadata;


  //constructor

  public Hecho(String titulo, String desc, String categoria, Ubicacion ubicacion, LocalDate fechaHecho, Perfil autor, Boolean anonimidad, List<Multimedia> multimedia) {
    this.titulo = titulo;
    this.descripcion = desc;
    this.categoria = categoria;
    this.ubicacion = ubicacion;
    this.fechaHecho = fechaHecho;
    this.fechaCarga = LocalDate.now();
    this.fechaModificacion = LocalDate.now();
    //this.fuente = fuente
    this.autor = autor;
    this.anonimo = anonimidad;
    this.eliminado = false;
    this.multimedia = multimedia;
    this.metadata = new LinkedList<>();


  }

  public Boolean tieneEtiqueta(String etiqueta)
  {
    return metadata.contains(etiqueta);
  }
  
  //setters

  // TODO: agregar al diagrama de clases
  public void editarHecho(SolicitudEdicion solicitud)
  {
      if(solicitud.getTituloMod() != null) {this.titulo = solicitud.getTituloMod();}
      if(solicitud.getDescMod() != null){this.descripcion = solicitud.getDescMod();}
      if(solicitud.getCategoriaMod() != null){this.categoria = solicitud.getCategoriaMod();}
      if(solicitud.getUbicacionMod() != null){this.ubicacion = solicitud.getUbicacionMod();}
      if(solicitud.getAnonimidadMod() != null){this.anonimo = solicitud.getAnonimidadMod();}
      this.fechaModificacion = LocalDate.now();
  }
  public void aniadirEtiqueta(String etiqueta) {
    this.metadata.add(etiqueta);
  }
  // TODO: agregar al diagrama de clases
//  public void actualizarse(String )
//  {
//
//  }

//  public String getNombreAutor()
//  {
//    Perfil.
//  }

}
