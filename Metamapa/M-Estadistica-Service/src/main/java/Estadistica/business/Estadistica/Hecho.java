package Estadistica.business.Estadistica;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Getter
public class Hecho {
    @Id
    @Getter
    public Integer id;
    @Getter
    public String titulo;
    @Getter
    public String descripcion;
    @Getter
    public String categoria;
    @Getter
    public Float latitud;
    @Getter
    public Float longitud;
    @Getter
    public LocalDate fechaHecho;
    @Getter
    public LocalDate fechaCarga;
    @Getter
    public LocalDate fechaModificacion;
    @Getter
    public Integer perfilId;
    @Getter
    public Boolean anonimo;
    @Getter
    public Boolean eliminado;
    @Getter
    public Map<String,String> multimedia;
    @Getter
    public Map<String,String> metadata;
    @Getter
    public Integer idFuente;
//  @Getter  @OneToMany(mappedBy = "hecho", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<SolicitudEliminacion> solicitudesEliminacion = new ArrayList<>();

    public Hecho(
            Integer id,
            String titulo,
            String descripcion,
            String categoria,
            Float latitud,
            Float longitud,
            LocalDate fechaHecho,
            LocalDate fechaCarga,
            LocalDate fechaModificacion,
            Integer perfilId,
            Boolean anonimo,
            Boolean eliminado,
            Map<String,String> multimedia,
            Map<String,String> metadata,
            Integer idFuente
    ){
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.latitud = latitud;
        this.longitud = longitud;
        this.fechaHecho = fechaHecho;
        this.fechaCarga = fechaCarga;
        this.fechaModificacion = fechaModificacion;
        this.perfilId = perfilId;
        this.anonimo = anonimo;
        this.eliminado = eliminado;
        this.multimedia = multimedia;
        this.metadata = metadata;
        this.idFuente = idFuente;

    }

}