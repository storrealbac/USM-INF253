package tarea3;

import java.util.List;

public class Jugador extends Personaje {
 
    private List<Item> items_aplicados;

    public Jugador(String nombre) {
        super();

        this.setNombre(nombre);
        this.setDinero(500);
        this.setHPActual(20);
        this.setHPTotal(20);
        this.setDanio(5);
        this.setDefensa(1);
    }

    void verEstado() {
        System.out.println(" - Estadisticas actuales -");
        System.out.println("    Dinero: " + this.getDinero());
        System.out.println("    HP: " + this.getHPActual() + "/" + this.getHPTotal());
        System.out.println("    DMG: " + this.getDanio());
        System.out.println("    Defensa: " + this.getDefensa());
    }

    void verItems() {
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

    // Getters
    List<Item> getItemsAplicados() {
        return this.items_aplicados;
    }

}
