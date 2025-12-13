package Estadistica.business.Estadistica;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(schema = "stats_schema")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Estadistica {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  int id;
  @Getter
  LocalDateTime fechaDeMedicion;

  public Estadistica() {
      this.fechaDeMedicion = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
    // Imprime algo como: 2025-12-13T20:00, JPA lo guarda como 2025-12-13 20:00:00
  }

}