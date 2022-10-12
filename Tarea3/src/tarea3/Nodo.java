package tarea3;

import java.util.ArrayList;
import java.util.List;

public abstract class Nodo {
    
    private Integer id;
    private List<Nodo> siguientes_nodos = new ArrayList<Nodo>();

    abstract void interactuar(Jugador jugador);

    public Nodo(Integer id) {
        this.setID(id);
    }

    // Agrega un nodo
    void agregarNodo(Nodo nodo) {
        siguientes_nodos.add(nodo);
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
