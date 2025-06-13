package domain.business.criterio;

import lombok.Getter;

import domain.business.Agregador.Agregador;
import domain.business.incidencias.Hecho;
import java.util.*;
//import org.apache.commons.lang
import java.util.UUID;

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

    public Coleccion(String titulo, String desc, List<Criterio> pertenencia, List<Criterio> noPertenencia,Agregador agregador){
        this.titulo=titulo;
        this.descripcion = desc;
        this.criterioPertenencia = pertenencia;
        this.criterioNoPertenencia = noPertenencia;
        this.agregador=agregador;
        this.handle = String.valueOf(UUID.randomUUID());
    }

    public void agregarCriterioPertenencia(Criterio criterio){
        this.criterioPertenencia.add(criterio);
    }
    public void eliminarCriterioPertenencia(Criterio criterio){
        this.criterioPertenencia.remove(criterio);
    }

    public void agregarCriterioNoPertenencia(Criterio criterio){
        this.criterioNoPertenencia.add(criterio);
    }
    public void eliminarCriterioNoPertenencia(Criterio criterio){
        this.criterioNoPertenencia.remove(criterio);
    }


    /*public List<Hecho> filtrarPorCriterios(){
        List<Hecho> hechos = agregador.getListaDeHechos();
        return hechos.stream()
            .filter(h -> this.getCriterioPertenencia().stream().allMatch(c -> c.cumple(h)))
            .filter(h -> this.getCriterioNoPertenencia().stream().noneMatch(c -> c.cumple(h)))
            .toList();
    }
    public List<Hecho> filtrarPorCriterios(List<Criterio> criterioPertenenciaAdicional, List<Criterio> criterioNoPertenenciaAdicional){
        List<Hecho> hechos = agregador.getListaDeHechos();
        return hechos.stream()
            .filter(h -> this.getCriterioPertenencia().add(criterioPertenenciaAdicional).stream().allMatch(c -> c.cumple(h)))
            .filter(h -> this.getCriterioNoPertenencia().add(criterioNoPertenenciaAdicional).stream().noneMatch(c -> c.cumple(h)))
            .toList();
    }*/

    public List<Hecho> filtrarPorCriterios(List<Criterio> criterioPertenenciaAdicional, List<Criterio> criterioNoPertenenciaAdicional) {
        List<Hecho> hechos = agregador.getListaDeHechos();

        List<Criterio> criteriosPertenenciaCombinados = new ArrayList<>(this.getCriterioPertenencia());
        if (!criterioPertenenciaAdicional.isEmpty())
            criteriosPertenenciaCombinados.addAll(criterioPertenenciaAdicional);

        List<Criterio> criteriosNoPertenenciaCombinados = new ArrayList<>(this.getCriterioNoPertenencia());
        if (!criterioNoPertenenciaAdicional.isEmpty())
            criteriosNoPertenenciaCombinados.addAll(criterioNoPertenenciaAdicional);

        return hechos.stream()
            .filter(h -> criteriosPertenenciaCombinados.stream().allMatch(c -> c.cumple(h)))
            .filter(h -> criteriosNoPertenenciaCombinados.stream().noneMatch(c -> c.cumple(h)))
            .toList();
    }


}