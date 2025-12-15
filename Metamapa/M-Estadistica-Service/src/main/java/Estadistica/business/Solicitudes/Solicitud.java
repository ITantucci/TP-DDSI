package Estadistica.business.Solicitudes;

import Estadistica.business.Hechos.Hecho;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter @Getter

@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Solicitud {
  @ManyToOne(fetch = FetchType.LAZY)
  private Hecho hechoAfectado;
  private String estado;
  //UUID id;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  public Solicitud(Hecho hechoAfectado, String estado) {
    this.hechoAfectado = hechoAfectado;
    this.estado = estado;
    //this.id = UUID.randomUUID();
  }

  public Solicitud() {}

  public void aceptarSolicitud() {
    this.estado = "APROBADA";
  }

  public void rechazarSolicitud() {
    this.estado = "RECHAZADA";
  }
}