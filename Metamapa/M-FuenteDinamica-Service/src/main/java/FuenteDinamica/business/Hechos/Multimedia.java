package FuenteDinamica.business.Hechos;
import lombok.*;

@Getter @Setter
public class Multimedia {
  public TipoMultimedia tipoMultimedia;
  public String  path;

  public Multimedia(){}

  public Multimedia(TipoMultimedia tipoMultimedia, String path) {
    this.tipoMultimedia = tipoMultimedia;
    this.path = path;
  }
}