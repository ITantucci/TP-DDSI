package Metamapa.DTO;

import Metamapa.business.Hechos.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.util.*;

@Getter @Setter
public class HechoDTO {
  @NotBlank
  private String titulo;
  private String descripcion;
  private String categoria;
  private Float latitud;
  private Float longitud;
  // yyyy-MM-dd en JSON
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate fechaHecho;
  @Size(max = 100)
  private String autor;
  private Boolean anonimo = Boolean.FALSE;

  // Lista de multimedia
  private List<MultimediaItem> multimedia = new ArrayList<>();

  // ——— Helper
  public List<Multimedia> toMultimediaDomain() {
    if (multimedia == null) return List.of();
    List<Multimedia> out = new ArrayList<>();
    for (MultimediaItem it : multimedia) {
      if (it == null || it.getTipoMultimedia() == null || it.getPath() == null || it.getPath().isBlank()) continue;
      Multimedia m = new Multimedia();
      m.setTipoMultimedia(it.getTipoMultimedia());
      m.setPath(it.getPath().trim());
      out.add(m);
    }
    return out;
  }

  // ——— Validación simple de par lat/lon ———
  public void validarCoordenadas() {
    if ((latitud == null) ^ (longitud == null)) {
      throw new IllegalArgumentException("Si enviás coordenadas, deben incluir latitud y longitud.");
    }
  }

  // DTO interno para multimedia
  @Getter @Setter
  public static class MultimediaItem {
    private TipoMultimedia tipoMultimedia; // FOTO, VIDEO, AUDIO
    private String path;                   // URL o ruta
  }
}
