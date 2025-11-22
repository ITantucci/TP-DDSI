package Estadistica.business.Estadistica;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
public class SolicitudEliminacion{
    @Getter
    public String motivo;
    @Getter @ManyToOne
    private Hecho hechoAfectado;
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    boolean spam;

    public SolicitudEliminacion(){}
    public SolicitudEliminacion(String motivo,Hecho hecho, boolean spam) {
        this.hechoAfectado = hecho;
        this.motivo = motivo;
        this.spam = spam;
    }
}