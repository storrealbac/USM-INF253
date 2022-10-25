package tarea3;

import java.util.ArrayList;
import java.util.List;

public class NodoTienda extends Nodo {
    
    private List<Item> inventario = new ArrayList<Item>();

    public NodoTienda(Integer id) {
        super(id);

        // Generando items de forma aleatoria
        Integer cantidad_items = Util.getRandomNumber(5, 7);
        for (Integer i = 0; i < cantidad_items; i++)
            inventario.add(generarItemAleatorio());
    }

    private static Item generarItemAleatorio() {
        Integer precio, recuperar_hp, aumentar_hp_total, aumentar_danio, aumentar_defensa;
        precio = Util.getRandomNumber(20, 30);
        recuperar_hp = Util.getRandomNumber(0, 10);
        aumentar_danio = Util.getRandomNumber(0, 10);
        aumentar_hp_total = Util.getRandomNumber(0, 10);
        aumentar_defensa = Util.getRandomNumber(0, 10);

        Item nuevo_item = new Item(precio, recuperar_hp, aumentar_hp_total, aumentar_danio, aumentar_defensa);
        return nuevo_item;
    }

    void interactuar(Jugador jugador) {
        System.out.println(" - Inventario de la tienda -");
    
        Integer numero = 1;
        for (Item it : inventario) {
            System.out.println(numero++ + ".- Precio: $" + it.getPrecio());
            System.out.println("  HP:" + it.getRecuperarHP());
            System.out.println("  Aumento de HP:" + it.getAumentarHPTotal());
            System.out.println("  Aumento de DMG:" + it.getAumentarDanio());
            System.out.println("  Aumento de defensa:" + it.getAumentarDefensa());
        }
    }
    
    // el indice esta aumentado en 1
    // ya que las listas son de 0 a n-1
    void comprar(Integer compra, Jugador jugador) {
        Integer indice = compra-1;

        if (indice < 0 || indice >= inventario.size()) {
            System.out.println("El objeto indicado esta fuera de rango");
            return;
        }

        // aplicar el item
        Item item_a_aplicar = this.inventario.get(indice);
        item_a_aplicar.aplicar(jugador);
        this.inventario.remove((int)indice);
    }

    // Getters
    List<Item> getInventario() {
        return this.inventario;
    }
}
