package tarea3;

public class NodoCombate extends Nodo {
    private Personaje enemigo;

    public NodoCombate(Integer id) {
        super(id);
        enemigo = new Personaje(true);
    }

    /**
     * Interactua con el nodo combate: Pelea con el enemigo del nivel
     * 
     * @param jugador: Jugador que interactuara
     * 
     * @return void
     */
    void interactuar(Jugador jugador) {
        jugador.combate(enemigo);
    }

    // Todos los getters
    Personaje getEnemigo() {
        return this.enemigo;
    }
    
    // Todos los setters
    void setEnemigo(Personaje enemigo) {
        this.enemigo = enemigo;
    }
}
