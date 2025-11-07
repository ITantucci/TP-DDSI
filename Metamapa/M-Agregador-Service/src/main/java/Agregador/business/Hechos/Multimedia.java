package Agregador.business.Hechos;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
public class Multimedia {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  public TipoMultimedia tipoMultimedia;
  public String path;

  public Multimedia(){}

  public Multimedia(TipoMultimedia tipoMultimedia, String path) {
    this.tipoMultimedia = tipoMultimedia;
    this.path = path;
  }
}