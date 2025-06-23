package domain.business.Agregador;
import domain.business.FuentesDeDatos.FuenteDeDatos;
import java.util.ArrayList;
import lombok.Getter;
import java.util.List;
import domain.business.incidencias.Hecho;

public class Agregador {
    @Getter
    private List<FuenteDeDatos> fuenteDeDatos;

    @Getter
    private ArrayList<Hecho> listaDeHechos;

    public Hecho actualizarHecho(Hecho hecho){
        // TODO: Hacer que hecho se actualice cuando se sobreescribe o se edita (NO PARA ESTA ENNTREGA)

        return hecho;
    };

    public Agregador(List<FuenteDeDatos> fuentes) {
        this.fuenteDeDatos = fuentes;
        this.actualizarHechos();
    }

    public void actualizarHechos(){
        var listaHechos = new ArrayList<Hecho>();
        fuenteDeDatos.forEach(f -> listaHechos.addAll(f.getHechos()));
        this.listaDeHechos = listaHechos;
    }
}
