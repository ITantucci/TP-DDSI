package infrastructure.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class HechoDTO {
    @Getter
    private String titulo;
    @Getter
    private String descripcion;
    @Getter
    private String categoria;
    @Getter
    private float latitud;
    @Getter
    private float longitud;
    @Getter
    private LocalDate fechaHecho;
    @Getter
    private LocalDate fechaCarga;
    @Getter
    private LocalDate fechaModificacion;
    @Getter
    private String fuente;
    @Getter
    private String autor; // o "perfil" dependiendo del modelo
    @Getter
    private boolean anonimo;
    @Getter
    private boolean eliminado;
    @Getter
    private List<String> multimedia; // path a los archivos
    @Getter
    private HashMap<String, String> metadata;

    // Constructores, getters y setters
    public HechoDTO() {}

    public HechoDTO(String titulo, String descripcion, String categoria, float latitud, float longitud,
                    LocalDate fechaHecho, LocalDate fechaCarga, LocalDate fechaModificacion,
                    String fuente, String autor, boolean anonimo, boolean eliminado,
                    List<String> multimedia, HashMap<String, String> metadata) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.latitud = latitud;
        this.longitud = longitud;
        this.fechaHecho = fechaHecho;
        this.fechaCarga = fechaCarga;
        this.fechaModificacion = fechaModificacion;
        this.fuente = fuente;
        this.autor = autor;
        this.anonimo = anonimo;
        this.eliminado = eliminado;
        this.multimedia = multimedia;
        this.metadata = metadata;
    }
}
