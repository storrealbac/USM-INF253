package tarea3;

import java.util.List;

public class NodoTienda extends Nodo {
    
    private List<Item> inventario;

    public NodoTienda(Integer id) {
        super(id);
    }

    void interactuar(Jugador jugador) {

    }
    
    void comprar(Integer compra) {
        
    }

    // Getters
    List<Item> getInventario() {
        return this.inventario;
    }
}
