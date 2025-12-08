package Agregador.DTO;
import Agregador.business.Hechos.Multimedia;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MultimediaDTO {
  private String tipoMultimedia;
  private String path;

  public MultimediaDTO(Multimedia m) {
    this.tipoMultimedia = String.valueOf(m.getTipoMultimedia());
    this.path = m.getPath();
  }
}