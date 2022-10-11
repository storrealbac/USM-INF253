package tarea3;

import java.util.List;

public abstract class Nodo {
    
    private Integer id;
    private List<Nodo> siguientes_nodos;

    abstract void interactuar(Jugador jugador);

    void agregarNodo(Nodo nodo) {

    }


    List<Nodo> getSiguientesNodos() {
        return this.siguientes_nodos;
    }

    // Getters
    Integer getID() {
        return this.id;
    }

    // Setters
    void setID(Integer id) {
        this.id = id;
    }

}
