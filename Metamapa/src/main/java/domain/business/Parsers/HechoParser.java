package domain.business.Parsers;

import domain.business.incidencias.Hecho;
import java.util.List;

public interface HechoParser {

    List<Hecho> parsearHecho(String path);
}
