package Estadistica.persistencia;

import Estadistica.business.Colecciones.Criterio;
import Estadistica.business.Hechos.Hecho;
import java.util.List;
import java.util.Optional;

public interface RepositorioHechosCustom {
  List<Hecho> filtrarPorCriterios(List<Criterio> criterios);
  Optional<String> obtenerHoraConMasHechos(String categoria);
  long contarHechosPorHora(String horaHH);
  Optional<String> obtenerCategoriaConMasHechos();
  List<String> obtenerCategorias();
}
