package tarea3;

public class NodoInicial extends Nodo {
    
    public NodoInicial(Integer id) {
        super(id);
    }

    void interactuar(Jugador jugador) {
        System.out.println(
            "\nBienvenido " + jugador.getNombre() + ", eres un sansano promedio, esta es la historia de un jugador que quería "+
            "pasar el ramo de Lenguajes de Programación. El problema es que aun no has saldado tu cuenta con el ramo de Programación 1 "+
            "y Kiwi quiere su parte. Tendrás que luchar con todo lo que se presente en el camino."
            );
    }

}
