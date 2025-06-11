package domain.business.criterio;

import domain.business.incidencias.Hecho;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import domain.business.Agregador.Agregador;
import lombok.Setter;

@Getter@Setter
public class Coleccion {
    private String titulo;
    private String descripcion;
    private List<Criterio> criterioPertenencia;
    private List<Criterio> criterioNoPertenencia;
    private Agregador agregador;
    private String handle;

    public void agregarCriterioPertenencia(Criterio criterio){
        this.criterioPertenencia.add(criterio);
    }
    public void eliminarCriterioPertenencia(Criterio criterio){
        this.criterioPertenencia.remove(criterio);
    }
    //TODO: devuelve una lista de hechos?
    public List<Hecho> filtrarPorCriterios(List<Criterio> criterios){
        List<Hecho> hechos = agregador.getListaDeHechos();
        return hechos.stream()
                .filter(h -> criterioPertenencia.stream().allMatch(c -> c.cumple(h)))
                .filter(h -> criterioNoPertenencia.stream().noneMatch(c -> c.cumple(h)))
                .toList();
    }


}
