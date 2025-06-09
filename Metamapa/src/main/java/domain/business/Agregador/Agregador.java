package domain.business.Agregador;

import domain.business.criterio.CriterioFuenteDeDatos;
import lombok.Getter;
import java.util.List;
import domain.business.incidencias.Hecho;

public class Agregador {
    @Getter
    private List<CriterioFuenteDeDatos> fuenteDeDatos;

    @Getter
    private List<Hecho> listaDeHechos;

    public Hecho actualizarHecho(Hecho hecho){
        // TODO: Hacer que hecho se actualice cuando se sobreescribe o se edita (NO PARA ESTA ENNTREGA)

        return hecho;
    };
}
