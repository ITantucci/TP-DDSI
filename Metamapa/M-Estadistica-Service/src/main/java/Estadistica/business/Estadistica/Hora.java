package Estadistica.business.Estadistica;
import java.time.LocalTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
public class Hora {
    @Getter @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    int ID;
    @Getter
    LocalTime hora;

    public Hora() {}
    public Hora(LocalTime hora) {
        this.hora = hora;
    }
}