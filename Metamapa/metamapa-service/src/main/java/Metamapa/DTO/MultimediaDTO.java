package Metamapa.DTO;

import Metamapa.business.Hechos.*;
import lombok.*;

@Getter @Setter
public class MultimediaDTO {
  private String tipoMultimedia;
  private String path;

  public Multimedia toDomain() {
    return new Multimedia(
            TipoMultimedia.valueOf(tipoMultimedia),
            path
    );
  }
}