package Metamapa.DTO;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigInteger;

// DTO para el request
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudEliminacionDTO {
  @NotBlank
  private BigInteger idHechoAfectado;

  @NotBlank
  @Size(min = 500, message = "El motivo debe tener al menos 500 caracteres")
  private String motivo;

  private String url; // opcional
}