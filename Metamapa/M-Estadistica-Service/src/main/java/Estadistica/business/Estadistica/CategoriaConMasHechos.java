package Estadistica.business.Estadistica;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(schema = "stats_schema")
public class CategoriaConMasHechos extends Estadistica{
  @Getter
  String categoria;

  public CategoriaConMasHechos(){}

  public CategoriaConMasHechos(String categoria) {
    super();
    this.categoria = categoria;
  }
}