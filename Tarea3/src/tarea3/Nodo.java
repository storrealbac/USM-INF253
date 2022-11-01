package tarea3;

import java.util.ArrayList;
import java.util.List;

public abstract class Nodo {
    
    private Integer id;
    private List<Nodo> siguientes_nodos = new ArrayList<Nodo>();

    // Estar\'a comentado por las clases hijas
    abstract void interactuar(Jugador jugador);

    public Nodo(Integer id) {
        this.setID(id);
    }

    /**
     * Agregar como vecino a un nodo
     * @param nodo: Nodo a agregar como vecino
     */
    void agregarNodo(Nodo nodo) {
        siguientes_nodos.add(nodo);
    }

    /**
     * Obtener los nodos siguientes
     * 
     * @return List<Nodo>: Lista de nodos
     */
    List<Nodo> getSiguientesNodos() {
        return this.siguientes_nodos;
    }

    // Todos los getters
    Integer getID() {
        return this.id;
    }

    // Todos los setters
    void setID(Integer id) {
        this.id = id;
    }

}
