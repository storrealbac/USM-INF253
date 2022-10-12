package tarea3;

public class NodoCombate extends Nodo {
    private Personaje enemigo;

    public NodoCombate(Integer id) {
        super(id);
    }

    void interactuar(Jugador jugador) {

    }

    Personaje getEnemigo() {
        return this.enemigo;
    }
    
    void setEnemigo(Personaje enemigo) {
        this.enemigo = enemigo;
    }
}
