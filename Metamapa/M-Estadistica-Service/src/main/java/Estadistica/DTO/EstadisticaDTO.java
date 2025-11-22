package Estadistica.DTO;
import lombok.*;
import java.util.Map;

@Getter @Setter
public class EstadisticaDTO {
  // Categoría más reportada
  private String categoriaMasReportada;
  private long cantidadCategoriaMasReportada;

  // Provincia con más hechos (en general o por colección)
  private String provinciaMasReportada;
  private long cantidadProvinciaMasReportada;

  // Provincia con más hechos para una categoría concreta
  private String provinciaPorCategoria;
  private long cantidadProvinciaPorCategoria;

  // Distribución horaria de hechos para una categoría (ej: {"00":5,"01":2,...})
  private Map<Integer, Long> hechosPorHoraCategoria;

  // Cantidad de solicitudes de eliminación que son spam
  private long cantidadSolicitudesSpam;
}