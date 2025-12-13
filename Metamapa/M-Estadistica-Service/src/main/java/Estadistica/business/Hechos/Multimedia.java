package Estadistica.business.Hechos;

import Estadistica.business.Hechos.Hecho;
import Estadistica.business.Hechos.TipoMultimedia;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Multimedia {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  public TipoMultimedia tipoMultimedia;
  public String path;
  @ManyToOne
  @JoinColumn(name = "hecho_id")
  private Hecho hecho;

  public Multimedia(){}

  public Multimedia(TipoMultimedia tipoMultimedia, String path) {
    this.tipoMultimedia = tipoMultimedia;
    this.path = path;
  }
}