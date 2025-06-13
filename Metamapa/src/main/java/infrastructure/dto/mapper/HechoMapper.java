package infrastructure.dto.mapper;

import domain.business.incidencias.Hecho;
import domain.business.incidencias.Ubicacion;
import infrastructure.dto.HechoDTO;

import java.util.ArrayList;

public class HechoMapper {

  public static Hecho fromDTO(HechoDTO dto) {
    return new Hecho(
        dto.getTitulo(),
        dto.getDescripcion(),
        dto.getCategoria(),
        dto.getUbicacion().getLatitud(),
        dto.getUbicacion().getLongitud(),
        dto.getFechaHecho(),
        dto.getFuenteDeDatos(),
        dto.getAutor(),
        dto.getAnonimo(),
        dto.getEliminado(),
        dto.getMultimedia());
  }
}