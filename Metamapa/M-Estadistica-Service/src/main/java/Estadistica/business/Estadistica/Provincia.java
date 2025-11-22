package Estadistica.business.Estadistica;
import jakarta.persistence.*;
import lombok.*;

@Entity
public class Provincia {
    @Getter @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    int ID;
    @Getter
    String Nombre;

    public Provincia() {}
    public Provincia(String Nombre) {
        this.Nombre = Nombre;
    }
}