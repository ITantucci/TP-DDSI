package FuenteDinamica.DTO;
import FuenteDinamica.business.Hechos.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter @Setter
public class HechoDTO {
  @NotBlank
  private String titulo;
  @NotBlank
  private String descripcion;
  private String categoria;
  private Float latitud;
  private Float longitud;
  private LocalDate fechaHecho;
  @NotNull
  private Integer idUsuario;
  private Boolean anonimo = false;
  private List<MultimediaDTO> multimedia;

  public Hecho toDomain(Integer idFuenteDeDatos) {
    List<Multimedia> multimediaDomain = multimedia != null ? multimedia.stream().map(MultimediaDTO::toDomain).toList() : List.of();
    return new Hecho(
            titulo,
            descripcion,
            categoria,
            latitud,
            longitud,
            fechaHecho,
            idUsuario,
            idFuenteDeDatos,
            anonimo,
            multimediaDomain
    );
  }
}