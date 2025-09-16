package Agregador.Service;

import Agregador.business.Hechos.Hecho;
import Agregador.persistencia.RepositorioHechos;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

// Agregador/Service/ServiceAgregador.java
@Service
public class ServiceAgregador {

  private final ServiceFuenteDeDatos fuentes;        // orquesta requests a estática/dinámica/proxy
  private final RepositorioHechos repo;
  private final Normalizador normalizador;           // tu clase mejorada

  public ServiceAgregador(ServiceFuenteDeDatos fuentes,
                          RepositorioHechos repo,
                          Normalizador normalizador) {
    this.fuentes = fuentes;
    this.repo = repo;
    this.normalizador = normalizador;
  }

  // Devuelve el nombre de la categoría con más hechos reportados
  public String categoriaMasReportada() {
    var hechos = repo.getHechos();                      // List<Hecho>
    if (hechos == null || hechos.isEmpty()) return null;

    return hechos.stream()
            .map(Hecho::getCategoria)
            .filter(Objects::nonNull)
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
            .entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(null);
  }


}
