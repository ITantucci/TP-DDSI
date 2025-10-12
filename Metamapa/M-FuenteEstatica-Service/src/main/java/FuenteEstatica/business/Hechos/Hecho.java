package FuenteEstatica.business.Hechos;
import FuenteEstatica.business.FuentesDeDatos.FuenteEstatica;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
//import jakarta.persistence.*;

//@Entity
//@Table(name = "hecho")
@Getter @Setter
public class Hecho {
  //@Id
  //@GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String titulo;
  private String descripcion;
  private String categoria;
  private Float latitud;
  private Float longitud;
  private LocalDate fechaHecho;
  private LocalDate fechaCarga;
  //@ManyToOne
  //@JoinColumn(name = "fuente_id")
  private Integer fuenteId;
  static public Integer contadorID = 1;

  public Hecho(
          String titulo,
          String descripcion,
          String categoria,
          Float latitud,
          Float longitud,
          LocalDate fechaHecho,
          Integer fuenteId) {  // <--- aquí pasás la referencia, no el ID
    this.titulo = titulo;
    this.descripcion = descripcion;
    this.categoria = categoria;
    this.latitud = latitud;
    this.longitud = longitud;
    this.fechaHecho = fechaHecho;
    this.fechaCarga = LocalDate.now();
    this.id = contadorID++;
    this.fuenteId = fuenteId;
  }

  public Hecho() {}
}