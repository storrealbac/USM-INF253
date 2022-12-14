package tarea3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NodoTienda extends Nodo {
    
    private List<Item> inventario = new ArrayList<Item>();

    public NodoTienda(Integer id) {
        super(id);

        // Generando items de forma aleatoria
        Integer cantidad_items = Util.getRandomNumber(5, 7);
        for (Integer i = 0; i < cantidad_items; i++)
            inventario.add(Item.generarItemAleatorio());
    }

    /**
    * Interactua con el nodo Tienda: Muestra opciones en la tienda
    *
    * @param jugador: Jugador que interactuara
    *
    * @return void
    */
    void interactuar(Jugador jugador) {
        if (this.inventario.size() == 0) {
            System.out.println("No hay mas items en la tienda, cerrando la tienda");
            return;
        }
        System.out.println();
        System.out.println("Tu dinero: " + jugador.getDinero());
        System.out.println(" - Inventario de la tienda -");
        Integer numero = 1;
        for (Item it : inventario) {
            System.out.println(numero++ + ".- Precio: $" + it.getPrecio());
            it.getStats();
        }

        // Le aseguro que acá no cierro el scanner porque lo hago en el main; 
        // a pesar de que no se cierre en la clase, cerrar 2 veces un scanner con stream
        // de System.in provoca un error, por que se cierra dos veces el input stream
        Scanner sc = new Scanner(System.in);
        System.out.print("Elija la opcion que desea (Cualquier numero fuera de rango es para salir de la interaccion): ");
        Integer opcion = sc.nextInt();

        if (opcion > 0 && opcion <= inventario.size()) {
            //this.inventario.get(opcion-1).aplicar(jugador);
            //this.inventario.remove(opcion-1);
            comprar(opcion, jugador);
        } else {
            System.out.println("Fuera del rango de valores, saliendo de la tienda!");
        }
    }
    
    /**
     * Permite al jugador comprar algun objeto
     * @param compra: El numero de item que quiere comprar
     * @param jugador: Al jugador que se le aplicara el item a comprar
     */
    void comprar(Integer compra, Jugador jugador) {
        Integer indice = compra-1;

        if (indice < 0 || indice >= inventario.size()) {
            System.out.println("El objeto indicado esta fuera de rango");
            return;
        }

        // aplicar el item
        Item item_a_aplicar = this.inventario.get(indice);
        if ( jugador.getDinero() < item_a_aplicar.getPrecio() ) {
            System.out.println("No tienes el suficiente dinero!");
            this.interactuar(jugador);
            return;
        }

        jugador.setDinero(jugador.getDinero() - item_a_aplicar.getPrecio());
        System.out.println("El item ha costado " + item_a_aplicar.getPrecio() + " monedas!");
        item_a_aplicar.aplicar(jugador);
        this.inventario.remove((int)indice);
        this.interactuar(jugador);
    }

    // Todos los getters
    List<Item> getInventario() {
        return this.inventario;
    }
}
