package Estadistica.business.Solicitudes;
import jakarta.persistence.*;
import lombok.Getter;
import Estadistica.business.Hechos.Hecho;

@Entity
@Table(name = "solicitud")
public class SolicitudEliminacion {
    @Getter
    public String motivo;
    @Getter @ManyToOne
    private Hecho hechoAfectado;
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Getter
    String estado;

    public SolicitudEliminacion(){}
    public SolicitudEliminacion(String motivo, Hecho hecho, String estado) {
        this.hechoAfectado = hecho;
        this.motivo = motivo;
        this.estado = estado;
    }
}