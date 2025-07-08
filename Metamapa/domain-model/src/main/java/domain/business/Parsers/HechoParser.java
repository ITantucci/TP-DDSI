package domain.business.Parsers;

import domain.business.incidencias.Hecho;
import java.io.InputStream;
import java.util.ArrayList;

public interface HechoParser {

    ArrayList<Hecho> parsearHechos(String path);

    ArrayList<Hecho> parsearHechos(InputStream in);
}
