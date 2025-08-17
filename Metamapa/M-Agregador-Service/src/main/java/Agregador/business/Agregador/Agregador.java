package Agregador.business.Agregador;

import Agregador.business.Solicitudes.SolicitudEdicion;
import Agregador.business.Solicitudes.SolicitudEliminacion;
import java.util.ArrayList;
import lombok.Getter;
import Agregador.business.Hechos.*;

public class Agregador {
    private static Agregador agregador = null;
    @Getter
    public ArrayList<Hecho> listaHechos;
    @Getter
    public ArrayList<SolicitudEliminacion> listaSolicitudesEliminacion;
    @Getter
    public ArrayList<SolicitudEdicion> listaSolicitudesEdicion;


    private Agregador() {
        this.listaHechos= new ArrayList<>();
        this.listaSolicitudesEliminacion = new ArrayList<>();
        this.listaSolicitudesEdicion = new ArrayList<>();

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

}
