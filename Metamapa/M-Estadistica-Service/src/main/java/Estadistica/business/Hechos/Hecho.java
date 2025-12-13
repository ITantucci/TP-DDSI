package Estadistica.business.Hechos;

import Estadistica.business.Hechos.Multimedia;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(
        name = "hecho",
        schema = "dbo",
        indexes = {
                @Index(name = "hecho_categoria", columnList = "categoria"),
                @Index(name = "hecho_fechaHecho", columnList = "fecha_hecho"),
                @Index(name = "hecho_lat_long", columnList = "latitud,longitud")
        }
)
@Getter @Setter
@NoArgsConstructor
public class Hecho {
    @Id
    @Column(name = "id", nullable = false)
    private BigInteger id;

    @Column(name = "titulo", length = 500, nullable = false)
    private String titulo;

    @Column(name = "descripcion", length = 4000)
    private String descripcion;

    @Column(name = "categoria", length = 200)
    private String categoria;

    @Column(name = "latitud")
    private Float latitud;

    @Column(name = "longitud")
    private Float longitud;

    @Column(name = "fecha_hecho", columnDefinition = "datetime2")
    private LocalDateTime fechaHecho;

    @Column(name = "fecha_carga", columnDefinition = "datetime2")
    private LocalDateTime fechaCarga;

    @Column(name = "fecha_modificacion", columnDefinition = "datetime2")
    private LocalDateTime fechaModificacion;

//    @Column(name = "perfil_usuario_id")
//    private Integer perfilId;

    @Column(name = "anonimo")
    private Boolean anonimo;

    @Column(name = "eliminado")
    private Boolean eliminado;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "hecho_id")
    private List<Multimedia> multimedia = new ArrayList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "hecho_metadata",
            schema = "dbo",
            joinColumns = @JoinColumn(name = "hecho_id")
    )
    @MapKeyColumn(name = "clave")
    @Column(name = "valor", length = 1000)
    private Map<String, String> metadata;


    public Hecho(
            BigInteger id,
            String titulo,
            String descripcion,
            String categoria,
            Float latitud,
            Float longitud,
            LocalDateTime fechaHecho,
            LocalDateTime fechaCarga,
            LocalDateTime fechaModificacion,
            Integer perfilId,
            Boolean anonimo,
            Boolean eliminado,
            ArrayList<Multimedia> multimedia,
            Map<String,String> metadata,
            Integer idFuente) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.latitud = latitud;
        this.longitud = longitud;
        this.fechaHecho = fechaHecho;
        this.fechaCarga = fechaCarga;
        this.fechaModificacion = fechaModificacion;
//        this.perfilId = perfilId;
        this.anonimo = anonimo;
        this.eliminado = eliminado;
        this.multimedia = multimedia;
        this.metadata = metadata;
       // this.idFuente = idFuente;
    }

    private static final BigInteger BASE = BigInteger.TEN.pow(12); // 10^12

    public Integer getIdFuente() {
        return this.id.divide(BASE).intValueExact();
    }

}