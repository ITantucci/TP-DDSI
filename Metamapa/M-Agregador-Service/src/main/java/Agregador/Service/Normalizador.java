package Agregador.Service;

import Agregador.business.Hechos.Hecho;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Normalizador {

  private final Map<String, String> diccionario;

  // Constructor recibe el diccionario de sinónimos
  public Normalizador(Map<String, String> diccionario) {
    this.diccionario = diccionario;
  }

  // Normaliza una categoría individual
  public String normalizarCategoria(String categoriaCruda) {
    if(categoriaCruda == null) return "desconocido";
    String clave = categoriaCruda.toLowerCase().trim();
    return diccionario.getOrDefault(clave, "desconocido");
  }

  // Normaliza hechos
  public List<Hecho> normalizar(List<Hecho> hechos) {
    for (Hecho h : hechos) {
      h.setCategoria(normalizarCategoria(h.getCategoria()));
    }
    return hechos;
  }
/*
  // Ejemplo de uso
  public static void main(String[] args) {
    // Diccionario básico de sinónimos
    Map<String, String> diccionario = new HashMap<>();
    diccionario.put("fuego", "Incendio");
    diccionario.put("incendios", "Incendio");
    diccionario.put("quemazón", "Incendio");
    diccionario.put("llamas", "Incendio");

    diccionario.put("inundaciones", "Inundación");
    diccionario.put("agua", "Inundación");
    diccionario.put("anegamiento", "Inundación");

    Normalizador normalizador = new Normalizador(diccionario);

    // Lista de hechos crudos
    List<Hecho> hechosCrudos = new ArrayList<>();
    hechosCrudos.add(new Hecho("Fuego en edificio", "Se incendió un edificio", "fuego", (float)-34.509,(float)-58.46, LocalDate.parse("09/07/2019", DateTimeFormatter.ofPattern("dd/MM/yyyy")),null,1,1,null,null));
    hechosCrudos.add(new Hecho("Anegamiento zona sur", "Calles inundadas", "agua", (float)-34.509,(float)-58.46, LocalDate.parse("09/07/2019", DateTimeFormatter.ofPattern("dd/MM/yyyy")),null,2,2,null,null));
    hechosCrudos.add(new Hecho("Incendios forestales", "Quemazón en el bosque", "incendios", (float)-34.509,(float)-58.46, LocalDate.parse("09/07/2019", DateTimeFormatter.ofPattern("dd/MM/yyyy")),null,3,3,null,null));
    hechosCrudos.add(new Hecho("Manifestación", "Protesta en plaza", "protesta", (float)-34.509,(float)-58.46, LocalDate.parse("09/07/2019", DateTimeFormatter.ofPattern("dd/MM/yyyy")),null,4,4,null,null));

    // Normalizar batch
    List<Hecho> hechosNormalizados = normalizador.normalizar(hechosCrudos);

    // Mostrar resultados
    hechosNormalizados.forEach(System.out::println);
  }*/
}