package Estadistica.business.Estadistica;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(schema = "stats_schema")
public class ProvinciaConMasHechosPorCategoria extends Estadistica{
  @Getter
  String provincia;
  @Getter
  String categoria;

  public ProvinciaConMasHechosPorCategoria() {}

  public ProvinciaConMasHechosPorCategoria(String provincia, String categoria) {
    super();
    this.provincia = provincia;
    this.categoria = categoria;
  }
}