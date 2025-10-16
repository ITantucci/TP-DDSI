package Agregador.DTO;

import Agregador.business.Consenso.Consenso;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsensoDTO {

  private Integer id;
  private String nombreTipo;
  private String descripcion;

  public ConsensoDTO(Consenso consenso) {
    if (consenso == null) return;
    this.id = consenso.getId();
    this.nombreTipo = consenso.getNombreTipo();

    // Descripción legible para mostrar en la UI
    switch (consenso.getNombreTipo()) {
      case "Absoluto" -> this.descripcion = "Se considera válido si todas las fuentes coinciden.";
      case "MayoriaSimple" -> this.descripcion = "Se considera válido si al menos la mitad de las fuentes coinciden.";
      case "MultiplesMenciones" -> this.descripcion = "Se considera válido si al menos dos fuentes coinciden sin contradicciones.";
      default -> this.descripcion = "Tipo de consenso personalizado: " + consenso.getNombreTipo();
    }
  }

  @Override
  public String toString() {
    return nombreTipo != null ? nombreTipo : "Sin consenso";
  }
}