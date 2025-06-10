package infrastructure.dto;

import lombok.Getter;
import java.time.LocalDate;

public class SolicitudEliminacionDTO {
    @Getter
    private String idHecho;
    @Getter
    private String motivo;
    @Getter
    private LocalDate fechaSolicitud;

    public SolicitudEliminacionDTO() {}

    public SolicitudEliminacionDTO(String idHecho, String motivo) {
        this.idHecho = idHecho;
        this.motivo = motivo;
        this.fechaSolicitud = LocalDate.now();
    }
}
