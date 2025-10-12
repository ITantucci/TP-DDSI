package Metamapa.business.Hechos;

import Metamapa.business.Usuarios.Usuario;
import lombok.*;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.*;

@Getter
public class Hecho {
  private String titulo;
  private String descripcion;
  private String categoria;
  private Float latitud;
  private Float longitud;
  private LocalDate fechaHecho;
  private LocalDate fechaCarga;
  private LocalDate fechaModificacion;
  @Setter
  private Usuario usuario;
  private BigInteger id;
  @Setter
  private Boolean anonimo;
  @Setter
  private Boolean eliminado;
  @Setter
  private ArrayList<Multimedia> multimedia;
  @Setter
  private HashMap<String, String> metadata;

  public Hecho(){}
  public Hecho(
      String titulo,
      String descripcion,
      String categoria,
      Float latitud,
      Float longitud,
      LocalDate fechaHecho,
      Usuario usuario,
      Integer fuenteId,
      Integer hechoId,
      Boolean anonimo,
      ArrayList<Multimedia> multimedia) {
    this.titulo = titulo;
    this.descripcion = descripcion;
    this.categoria = categoria;
    this.latitud = latitud;
    this.longitud = longitud;
    this.fechaHecho = fechaHecho;
    this.fechaCarga = LocalDate.now();
    this.fechaModificacion = LocalDate.now();
    this.usuario = usuario;
    this.anonimo = anonimo;
    this.eliminado = false;
    this.multimedia = multimedia;
    this.metadata = new HashMap<>();
    this.id = BigInteger.valueOf(fuenteId).multiply(BigInteger.TEN.pow(12)).add(BigInteger.valueOf(hechoId)); //TODO FuenteId tiene que venir de la siguiente froma xyyyyyy siendo x el tipo de fuente 1 para dinamica, 2 para estaica, 3 para proxy. y despues yyyyyy es el id de la fuente. esto se logra para sumandole 1000000 a un id de fuente dinamica, 2000000 para estatica y 3000000 para proxu
  }

  public Boolean tieneEtiqueta(String key, String value) {
    return getMetadata().get(key).equals(value);
  }

  public void editarHecho(String titulo, String descripcion, String categoria, Float latitud, Float longitud, LocalDate fechaHecho, Boolean anonimidad, ArrayList<Multimedia> multimedia) {
    if (titulo != null) {
      this.titulo = titulo;
    }
    if (descripcion != null) {
      this.descripcion = descripcion;
    }
    if (categoria != null) {
      this.categoria = categoria;
    }
    if (latitud != null & longitud != null) {
      this.latitud = latitud;
      this.longitud = longitud;
    }
    if (fechaHecho != null) {
      this.fechaHecho = fechaHecho;
    }
    if (anonimidad != null) {
      this.anonimo = anonimidad;
    }
    if (multimedia != null) {
      this.multimedia = multimedia;
    }
    this.fechaModificacion = LocalDate.now();
  }

  public void aniadirEtiqueta(String key, String value) {
    if (this.tieneEtiqueta(key, value)) {
      throw new RuntimeException("Esa etiqueta ya existe");
    } else this.metadata.put(key, value);
  }
}