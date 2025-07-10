package DTO;

import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.ArrayList;

@JsonTypeName("FUENTEESTATICA")

public class FuenteEstaticaDTO extends FuenteDeDatosDTO {

  public FuenteEstaticaDTO() {} // va a haber que usar dtos para no modificar la capa de negocio
  public FuenteEstaticaDTO(String nombre){
    this.nombre = nombre;
    this.id =contadorID++;
    // this.pathCSV = pathCSV;
    this.hechos = new ArrayList<>();
    this.tipoFuente = tipoFuente.FUENTEESTATICA;
  }
}
