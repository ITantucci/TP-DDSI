package Agregador.business.Solicitudes;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigInteger;

@Entity
@Setter @Getter
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Solicitud {
  //Hecho hechoAfectado;  // Hecho relacionado con la solicitud
  BigInteger hechoAfectado;
  EstadoSolicitud estado; // Estado de la solicitud (puede ser un enum o clase)
  //UUID id;
  @Id
  protected Integer id;
  static protected Integer contadorID = 1;

  public Solicitud(BigInteger hechoAfectado, EstadoSolicitud estado) {
    this.hechoAfectado = hechoAfectado;
    this.estado = estado;
    this.id = contadorID++;
    //this.id = UUID.randomUUID();
  }

  public Solicitud() {}

  public void aceptarSolicitud(){
    this.estado = EstadoSolicitud.APROBADA;
  }
  public  void rechazarSolicitud(){this.estado = EstadoSolicitud.RECHAZADA;
  }
}