package DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import domain.business.FuentesDeDatos.FuenteProxy;
import domain.business.FuentesDeDatos.TipoFuente;
import domain.business.externo.demo.Conexion;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Map;
import lombok.Getter;



@JsonTypeName("FUENTEDEMO")
public class FuenteDemoDTO extends FuenteProxyDTO {
  @Getter
  private LocalDateTime fechaUltimaConsulta;
  @Getter
  @JsonIgnore
  private Conexion conexion;
  public FuenteDemoDTO(){}
  public FuenteDemoDTO(String nombreFuente, String endpointBase) {
    super(nombreFuente, endpointBase);
    this.nombre = nombreFuente;
    this.hechos = new ArrayList<>();
    this.fechaUltimaConsulta = LocalDateTime.now(ZoneId.of("UTC")).minusHours(1);
    this.conexion = new Conexion(){
      @Override
      public Map<String, Object> siguienteHecho(String url, LocalDateTime fechaUltimaConsulta) {
        return null;
      }
    };
    this.id = contadorID++;
    this.tipoFuente = TipoFuente.FUENTEDEMO;
  }
}
