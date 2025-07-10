package DTO;

import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.ArrayList;

@JsonTypeName("FUENTEDINAMICA")
public class FuenteDinamicaDTO extends FuenteDeDatosDTO {

  public FuenteDinamicaDTO() {
    this.id = contadorID++;
    this.nombre = "Fuente Dinamica";
    this.hechos =  new ArrayList<>();
    this.tipoFuente = tipoFuente.FUENTEDINAMICA;
  }
}