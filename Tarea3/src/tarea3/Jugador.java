package tarea3;

import java.util.List;

public class Jugador extends Personaje {
 
    private List<Item> items_aplicados;

    public Jugador(String nombre) {
        this.setNombre(nombre);
        this.setDinero(500);
        this.setHPActual(20);
        this.setHPTotal(20);
        this.setDanio(5);
        this.setDefensa(1);
    }

    void verEstado() {

    }

    void verItems() {

    }

    // Getters
    List<Item> getItemsAplicados() {
        return this.items_aplicados;
    }

}
