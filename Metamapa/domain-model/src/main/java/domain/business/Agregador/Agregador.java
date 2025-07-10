package domain.business.Agregador;
import DTO.FuenteDeDatosDTO;
import DTO.HechoDTO;
import domain.business.FuentesDeDatos.FuenteDeDatos;
import java.util.ArrayList;
import lombok.Getter;
import domain.business.incidencias.Hecho;

public class Agregador {
    private static Agregador agregador = null;

    @Getter
    public ArrayList<FuenteDeDatosDTO> fuentesDeDatos;

    @Getter
    public ArrayList<HechoDTO> listaDeHechos;

    public void actualizarHechos() {
        ArrayList<HechoDTO> hechos = new ArrayList<>();
        fuentesDeDatos.forEach(f -> hechos.addAll(f.getHechos()));
        listaDeHechos = hechos;
    }

    private Agregador() {
        this.fuentesDeDatos= new ArrayList<>();
        this.listaDeHechos= new ArrayList<>();


//        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
//
//        Runnable tarea = () -> this.actualizarHechos();
//
//        scheduler.scheduleAtFixedRate(tarea, 0, 2, TimeUnit.HOURS);
        }
        //Instancio el agregador como singleton
        public static Agregador getInstance() {
        if (agregador == null)
            agregador = new Agregador();
            return agregador;
        }

    public void agregarFuenteDeDatos(FuenteDeDatosDTO fuente){
        if (!fuentesDeDatos.contains(fuente)){
            fuentesDeDatos.add(fuente);
            this.actualizarHechos();
        }
    }


    public void removerFuenteDeDatos(Integer idFuente){
        this.fuentesDeDatos.removeIf(f -> f.getId() == idFuente);
        this.actualizarHechos();
    }

}
