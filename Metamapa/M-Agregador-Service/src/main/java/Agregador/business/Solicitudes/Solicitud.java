package Agregador.business.Solicitudes;
import Agregador.business.Hechos.Hecho;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter @Getter
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Solicitud {
  @ManyToOne (cascade = CascadeType.ALL)
  private Hecho hechoAfectado;
  @Enumerated(EnumType.STRING)
  private EstadoSolicitud estado;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  public Solicitud(Hecho hechoAfectado, EstadoSolicitud estado) {
    this.hechoAfectado = hechoAfectado;
    this.estado = estado;
  }

  public Solicitud() {}

  public void aceptarSolicitud() {
    this.estado = EstadoSolicitud.APROBADA;
  }

  public void rechazarSolicitud() {
    this.estado = EstadoSolicitud.RECHAZADA;
  }
}