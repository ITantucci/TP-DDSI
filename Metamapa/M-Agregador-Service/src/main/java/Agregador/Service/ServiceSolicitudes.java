package Agregador.Service;
import Agregador.DTO.SolicitudEliminacionDTO;
import Agregador.business.Solicitudes.*;
import Agregador.persistencia.RepositorioAgregador;
import Agregador.persistencia.RepositorioSolicitudes;
import org.springframework.stereotype.Service;

@Service
public class ServiceSolicitudes {
    public enum Result { OK, NOT_FOUND, CONFLICT, INVALID }
    private final RepositorioAgregador repositorioAgregador;
    private final RepositorioSolicitudes repoSolicitudes;

    public ServiceSolicitudes(RepositorioAgregador ra, RepositorioSolicitudes rs) {
        this.repositorioAgregador = ra;
        this.repoSolicitudes = rs;
    }

    // @Transactional (opcional si usás BD)
    public Result aprobar(Integer id) {
        var s = repositorioAgregador.findSolicitudById(id);
        if (s == null) return Result.NOT_FOUND;
        if (s.getEstado() != EstadoSolicitud.PENDIENTE) return Result.CONFLICT;

        s.aceptarSolicitud(); // cambia a APROBADA, timestamps, etc.
        //repoAgregador.bloquearHecho(s.getHechoAfectado()); // no mostrar / no re-ingestar
        //repositorioAgregador.save(s);
        return Result.OK;
    }

    // @Transactional
    public Result rechazar(Integer id) {
        var s = repositorioAgregador.findSolicitudById(id);
        if (s == null) return Result.NOT_FOUND;
        if (s.getEstado() != EstadoSolicitud.PENDIENTE) return Result.CONFLICT;

        s.rechazarSolicitud();
        //repositorioAgregador.save(s);
        return Result.OK;
    }

    public SolicitudEliminacionDTO crearSolicitud(SolicitudEliminacionDTO dto) {
        SolicitudEliminacion solicitud = new SolicitudEliminacion(dto.getHechoAfectado(), dto.getMotivo());
        this.repoSolicitudes.save(solicitud);
        return new SolicitudEliminacionDTO(solicitud);
    }
}

