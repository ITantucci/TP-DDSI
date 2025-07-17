package Agregador.business.deprecado.Parsers;

import java.io.InputStream;
import java.util.ArrayList;
import Agregador.business.deprecado.incidencias.Hecho;

public interface HechoParser {

    ArrayList<Hecho> parsearHechos(String path, Integer fuenteID);

    ArrayList<Hecho> parsearHechos(InputStream in, Integer fuenteID);
}
