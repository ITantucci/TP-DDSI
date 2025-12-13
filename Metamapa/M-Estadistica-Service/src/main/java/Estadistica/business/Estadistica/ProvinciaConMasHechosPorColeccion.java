package Estadistica.business.Estadistica;
import jakarta.persistence.*;
import lombok.*;
import Estadistica.business.Colecciones.Coleccion;

@Entity
@Table(schema = "stats_schema")
public class ProvinciaConMasHechosPorColeccion extends Estadistica{
    @Getter
    String provincia;
    @Getter @ManyToOne
    Coleccion coleccion;

    public ProvinciaConMasHechosPorColeccion() {}

    public ProvinciaConMasHechosPorColeccion(String provincia, Coleccion coleccion) {
        super();
        this.provincia = provincia;
        this.coleccion = coleccion;
    }
}