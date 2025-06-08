package domain.business.tiposSolicitudes;
import domain.business.incidencias.Hecho;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public abstract class Solicitud {
  // Atributos
  protected Hecho hechoAfectado;  // Hecho relacionado con la solicitud
  private EstadoSolicitud estado; // Estado de la solicitud (puede ser un enum o clase)

  // Constructor
  public Solicitud(Hecho hechoAfectado, EstadoSolicitud estado) {
    this.hechoAfectado = hechoAfectado;
    this.estado = estado;
  }


  // Métodos abstractos (sin implementación en la clase base)
  public abstract void aceptarSolicitud();  // Métodos abstractos que las subclases deben implementar
  public abstract void rechazarSolicitud(); // Métodos abstractos que las subclases deben implementar

}