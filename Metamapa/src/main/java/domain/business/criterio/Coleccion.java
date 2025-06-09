package domain.business.criterio;

import lombok.Getter;

import java.util.List;
import domain.business.Agregador.Agregador;

public class Coleccion {
    @Getter
    private String titulo;
    @Getter
    private String descripcion;
    @Getter
    private List<Criterio> criterioPertenencia;
    @Getter
    private List<Criterio> criterioNoPertenencia;
    @Getter
    private Agregador agregador;
    @Getter
    private String handle;

    public void agregarCriterioPertenencia(Criterio criterio){
        this.criterioPertenencia.add(criterio);
    }
    public void eliminarCriterioPertenencia(Criterio criterio){
        this.criterioPertenencia.remove(criterio);
    }
    public void filtrarPorCriterios(List<Criterio> criterios){
        //TODO: filtrarPorCriterios
    }


}
