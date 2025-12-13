package Estadistica.business.Estadistica;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(schema = "stats_schema")
public class HoraConMasHechosPorCategoria extends Estadistica{
  @Getter
  String hora;
  @Getter
  String categoria;

  public HoraConMasHechosPorCategoria() {}

  public HoraConMasHechosPorCategoria(String hora, String categoria) {
    super();
    this.hora = hora;
    this.categoria = categoria;
  }
}