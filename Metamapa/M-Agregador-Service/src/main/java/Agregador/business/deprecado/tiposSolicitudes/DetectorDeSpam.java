package Agregador.business.deprecado.tiposSolicitudes;

public interface DetectorDeSpam {
    static boolean esSpam(String texto) {
        // Lógica simple para simular comportamiento
        return texto.length()<25;
    }
}