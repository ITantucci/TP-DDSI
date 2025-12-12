package FuenteEstatica.business.Parsers;

import FuenteEstatica.business.Hechos.Hecho;
import FuenteEstatica.business.FuentesDeDatos.FuenteEstatica;
import java.io.InputStream;
import java.util.ArrayList;

public interface HechoParser {
    ArrayList<Hecho> parsearHechos(String path, FuenteEstatica fuente);

    ArrayList<Hecho> parsearHechos(InputStream in, FuenteEstatica fuente);

    Hecho parse(String[] campos, FuenteEstatica fuente);
}