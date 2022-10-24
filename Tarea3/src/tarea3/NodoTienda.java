package tarea3;

import java.util.List;

public class NodoTienda extends Nodo {
    
    private List<Item> inventario;

    public NodoTienda(Integer id) {
        super(id);
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
        this.inventario.get(indice).aplicar(jugador);;
        this.inventario.remove((int)indice);
    }

    // Getters
    List<Item> getInventario() {
        return this.inventario;
    }
}
