package tarea3;

public class NodoJefeFinal extends Nodo {
    private Personaje jefe;
    
    public NodoJefeFinal(Integer id) {
        super(id);
    }

    void interactuar(Jugador jugador) {

    }

    // Getters
    Personaje getJefe() {
        return this.jefe;
    }

    // Setters
    void setJefe(Personaje jefe) {
        this.jefe = jefe;
    }
}
