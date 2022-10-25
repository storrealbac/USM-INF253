package tarea3;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class NodoEvento extends Nodo {
    private String descripcion, alternativa1, alternativa2;
    private Item resultado1, resultado2;
    
    public NodoEvento(Integer id) {
        super(id);
        inicializarEvento();
    }

    private void inicializarEvento() {
        List<String> descripciones = new ArrayList<String>();
        descripciones.add("Kiwi se ha convertido en un dios generoso y ha hecho un evento especial para ti, tienes que ser sabio y elegir la opcion correcta, o te arrepentiras!");
        descripciones.add("Has encontrado una guarida secreta de Kiwi y puedes aprovechar de robarle algunas cosas, tienes que ser cauteloso y elegir bien tu decision.");
        descripciones.add("Has encontrado a Kiwi desprotegido y puedes aprovechar de obtener algunos items, intenta elegir el que mas te acomode!");
        
        // Setea alguna descripcion random
        setDescripcion(descripciones.get(Util.getRandomNumber(0, descripciones.size()-1)));
        
        setAlternativa1("Cuidar a Kiwi");
        setAlternativa2("Robar a Kiwi");

        setResultado1(Item.generarItemAleatorio());
        setResultado2(Item.generarItemAleatorio());
    }

    void interactuar(Jugador jugador) {
        System.out.println("--- Descripcion de la Quest ---");
        System.out.println(" " + getDescripcion());
        
        System.out.println("Alternativas");
        System.out.println("1.- " + getAlternativa1());
        System.out.println("2.- " + getAlternativa2());

        // Le aseguro que acá no cierro el scanner porque lo hago en el main; 
        // a pesar de que no se cierre en la clase, cerrar 2 veces un scanner con stream
        // de System.in provoca un error, por que se cierra dos veces el input stream
        Scanner sc = new Scanner(System.in);
        System.out.print("Elija la opcion que desea: ");
        Integer opcion = sc.nextInt();        

        if (opcion > 2 || opcion < 0) {
            System.out.println(" Opción no válida, no se tomará en cuenta la decisión");
            interactuar(jugador);
            return;
        }

        System.out.println("Item obtenido y sus estadisticas!");
        if (opcion == 1) {
            getResultado1().getStats();
            getResultado1().aplicar(jugador);
        }
        else if (opcion == 2) {
            getResultado2().getStats();
            getResultado2().aplicar(jugador);
        }
        
    }

    // Getters
    String getDescripcion() {
        return this.descripcion;
    }

    String getAlternativa1() {
        return this.alternativa1;
    }

    String getAlternativa2() {
        return this.alternativa2;
    }

    Item getResultado1() {
        return this.resultado1;
    }

    Item getResultado2() {
        return this.resultado2;
    }

    // Setters
    void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    void setAlternativa1(String alternativa1) {
        this.alternativa1 = alternativa1;
    }

    void setAlternativa2(String alternativa2) {
        this.alternativa2 = alternativa2;
    }

    void setResultado1(Item resultado1) {
        this.resultado1 = resultado1;
    }

    void setResultado2(Item resultado2) {
        this.resultado2 = resultado2;
    }
}
