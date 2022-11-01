package tarea3;

import java.util.ArrayList;
import java.util.List;

public class Jugador extends Personaje {
 
    private List<Item> items_aplicados = new ArrayList<Item>();

    // Crear un jugador por defecto
    Jugador(String nombre) {
        super();

        this.setNombre(nombre);
        this.setDinero(500);
        this.setHPActual(20);
        this.setHPTotal(20);
        this.setDanio(5);
        this.setDefensa(1);
    }

    /**
    * Ver estado del jugador por la consola 
    * 
    * @return void
    */
    void verEstado() {
        System.out.println();
        System.out.println(" - Estadisticas actuales -");
        System.out.println("    Dinero: " + this.getDinero());
        System.out.println("    HP: " + this.getHPActual() + "/" + this.getHPTotal());
        System.out.println("    DMG: " + this.getDanio());
        System.out.println("    Defensa: " + this.getDefensa());
    }

    /**
    * Ver lista de items por consola
    * 
    * @return void
    */
    void verItems() {
        System.out.println();
        System.out.println(" - Items adquiridos -");
        Integer i = 1; // Numero de item
        for (Item it : items_aplicados) {
            System.out.println(i++ + ".-");
            System.out.println("  Recuperacion de HP: " + it.getRecuperarHP());
            System.out.println("  Aumento de HP Total: " + it.getAumentarHPTotal());
            System.out.println("  Aumento de DMG: " + it.getAumentarDanio());
            System.out.println("  Aumento de defensa: " + it.getAumentarDefensa());
        }
    }


    // Todos los getters
    List<Item> getItemsAplicados() {
        return this.items_aplicados;
    }

    // Todos los setters
    void agregarItem(Item item) {
        items_aplicados.add(item);
    }

}
