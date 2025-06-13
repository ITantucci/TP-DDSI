package domain.business.Parsers;

import domain.business.incidencias.Hecho;
import java.util.ArrayList;
import java.util.List;

public interface HechoParser {

    ArrayList<Hecho> parsearHecho(String path);
}
