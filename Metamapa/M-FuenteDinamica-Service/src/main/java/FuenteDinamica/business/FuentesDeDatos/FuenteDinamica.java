package FuenteDinamica.business.FuentesDeDatos;
import FuenteDinamica.business.Hechos.*;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.time.LocalDateTime;
import java.util.*;
import lombok.*;
import jakarta.persistence.*;

@NoArgsConstructor
@JsonTypeName("FUENTEDINAMICA")
@Entity
@Table(name = "fuente_dinamica")
@Getter @Setter
public class FuenteDinamica {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hecho_seq")
  @SequenceGenerator(
          name = "hecho_seq",
          sequenceName = "hecho_id_seq",
          initialValue = 10001,
          allocationSize = 1
  )
  private Integer fuenteId;
  private String nombre;
  @OneToMany(mappedBy = "fuente", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Hecho> hechos = new ArrayList<>();


  public FuenteDinamica(String nombre) {this.nombre = nombre; }

  public void agregarHecho(
          String titulo,
          String desc,
          String categoria,
          Float latitud,
          Float longitud,
          LocalDateTime fechaHecho,
          Integer idAutor,
          Boolean anonimidad,
          List<Multimedia> multimedia) {
    Hecho h = new Hecho(
            titulo,
            desc,
            categoria,
            latitud,
            longitud,
            fechaHecho,
            idAutor,
            this,
            anonimidad,
            multimedia
    );
    this.hechos.add(h);
    if (multimedia != null) {
      for (Multimedia m : multimedia) {
        m.setHecho(h);
      }
    }
  }
}