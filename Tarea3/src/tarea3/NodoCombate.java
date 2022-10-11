package tarea3;

public class NodoCombate extends Nodo {
    private Personaje enemigo;

    void interactuar(Jugador jugador) {

    }

    Personaje getEnemigo() {
        return this.enemigo;
    }
    
    void setEnemigo(Personaje enemigo) {
        this.enemigo = enemigo;
    }
}
