package org.utl.dsm.huellas_escritorio.Modelo;
import org.utl.dsm.huellas_escritorio.Modelo.Adoptante;
public class Sesion {
    private static Adoptante adoptanteActual;
    private static Animales animalActual;
    public static void setAdoptanteActual(Adoptante a) {
        adoptanteActual = a;
    }

    public static Adoptante getAdoptanteActual() {
        return adoptanteActual;
    }

    public static Animales getAnimalActual() {
        return animalActual;
    }

    public static void setAnimalActual(Animales animalActual) {
        Sesion.animalActual = animalActual;
    }

    public static boolean sesionIniciada() {
        return adoptanteActual != null;
    }

    public static void cerrarSesion() {
        adoptanteActual = null;
    }
}
