package FuenteEstatica.business.FuentesDeDatos;
import FuenteEstatica.business.Parsers.*;
import FuenteEstatica.business.Hechos.Hecho;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.io.*;
import java.util.*;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.Getter;
import lombok.*;

@JsonTypeName("FUENTEESTATICA")
//@Entity
//@Table(name = "fuente_estatica")
@Getter @Setter
public class FuenteEstatica {
  static protected Integer contadorID = 2000000;
  //@Id
  //@GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Integer fuenteId;
  public String nombre;
  //@OneToMany(mappedBy = "fuente", cascade = CascadeType.ALL, orphanRemoval = true)
  public ArrayList<Hecho> hechos;
  //public HechoParser hechoParser;

  public FuenteEstatica() {} // va a haber que usar dtos para no modificar la capa de negocio
  public FuenteEstatica(String nombre) {
    if (contadorID > 2999998) {
      throw new RuntimeException("No hay mas espacio para nuevas fuentes :(");
    } else {
      this.nombre = nombre;
      this.fuenteId = contadorID++;
      this.hechos = new ArrayList<>();
    }
  }

  public void cargar(String tipo,String path) {
    if (tipo.equals("CSV")) {
      this.hechos.addAll(new CSVHechoParser().parsearHechos(path, this));
      //case "JSON": new JSONHechoParser().parsearHechos(path, id).forEach((this::agregarHecho));  arreglar el codigo para que tome un JSON?
      //break;
    } else {
      this.hechos.addAll(new CSVHechoParser().parsearHechos(path, this));
    }
  }

  public void cargarHechos(String path) {
    try (CSVReader reader = new CSVReader(new FileReader(path))) {
      String[] fila;
      while ((fila = reader.readNext()) != null) {
        Hecho hecho = new CSVHechoParser().parse(fila, this);
        // buscar si ya existe un hecho con el mismo título
        Optional<Hecho> existente = hechos.stream()
                .filter(h -> h.getTitulo().equalsIgnoreCase(hecho.getTitulo()))
                .findFirst();
        if (existente.isPresent()) {
          // pisar los atributos del existente
          Hecho h = existente.get();
          h.setDescripcion(hecho.getDescripcion());
          h.setCategoria(hecho.getCategoria());
          h.setLatitud(hecho.getLatitud());
          h.setLongitud(hecho.getLongitud());
          h.setFechaHecho(hecho.getFechaHecho());
        } else {
          hechos.add(hecho);
        }
      }
    } catch (IOException | CsvValidationException e) {
      throw new RuntimeException("Error al cargar hechos desde CSV", e);
    }
  }
/*
  public void cargarJSON(String path) {
    new JSONHechoParser().parsearHechos(path, id).forEach((this::agregarHecho));
  }
*/
}
