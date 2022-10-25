package tarea3;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

import java.util.Scanner;

import GraphGenerator.GraphGenerator;
import GraphGenerator.Edge;


public class Mapa {
    
    private Integer profundidad;
    private NodoInicial nodo_inicial;
    private Nodo nodo_actual;

    private Map<Integer, ArrayList<Nodo>> profundidades;
    private SortedSet<Integer> visitados;

    public Mapa(Integer profundidad) {
        this.profundidad = profundidad;
        nodo_inicial = new NodoInicial(0);
        nodo_actual = nodo_inicial;

        profundidades = new HashMap<Integer, ArrayList<Nodo>>();
        visitados = new TreeSet<Integer>();

        generarMapa(nodo_inicial);
        DFS(nodo_inicial, 1);
    }

    private void generarMapa(NodoInicial nodo_inicial) {
        SortedSet<Edge> edges = GraphGenerator.Generar(profundidad);

        Integer cantidad_nodos = edges.last().y + 1;
        //System.out.println("Largo array: " + cantidad_nodos);

        Nodo[] tipos_nodos = new Nodo[cantidad_nodos];

        Random random = new Random();

        // empieza desde 1 pq el 0 es el inicial
        for (Integer i = 1; i < (cantidad_nodos - 1); i++) {
            Integer azar = random.nextInt(100) + 1;

            // Nodo tipo Evento
            if (azar <= 30)
                tipos_nodos[i] = new NodoEvento(i);

            // Nodo tipo Combate
            else if (azar <= 90)
                tipos_nodos[i] = new NodoCombate(i);

            // Nodo tipo Tienda
            else if (azar <= 100)
                tipos_nodos[i] = new NodoTienda(i);
        }

        // El primer y ultimo nodo
        tipos_nodos[0] = nodo_inicial;
        tipos_nodos[cantidad_nodos - 1] = new NodoJefeFinal(cantidad_nodos-1);

        // Se creó el mapa c:
        for (Edge e : edges) {
            //System.out.printf("(%d) -> (%d)\n", e.x, e.y);
            Integer id_desde = e.x, id_hasta = e.y;
            tipos_nodos[id_desde].agregarNodo(tipos_nodos[id_hasta]);
        }

    }

    // Como es un DAG, podemos hacer un DFS y ver la profundidad desde el nodo inicial en O(V + A)
    private void DFS(Nodo nodo, Integer profundidad) {
        profundidades.putIfAbsent(profundidad, new ArrayList<>());
        ArrayList<Nodo> lista_profundidades = profundidades.get(profundidad);

        boolean no_visitado = visitados.add(nodo.getID());

        if (no_visitado)
            lista_profundidades.add(nodo);

        for (Nodo vecino : nodo.getSiguientesNodos())
            DFS(vecino, profundidad + 1);
    }
    
    // ver los siguientes nodos
    void verMapa() {
        System.out.println();
        System.out.println(" --- Niveles del juego --- ");

        // Todos los niveles!
        for (Map.Entry<Integer, ArrayList<Nodo>> entry : profundidades.entrySet()) {
            System.out.println("Profundidad -> " + entry.getKey());
            
            for (Nodo nodo : entry.getValue()) {
                String tipo_nodo = nodo.getClass().getSimpleName().substring(4);
                System.out.println(" [ID] " + nodo.getID() + " [Tipo de quest] " + tipo_nodo);
            }
        }

        
        // Siguientes
        System.out.println();
        System.out.println("---- Siguientes quest desde la quest actual! ----");
        for (Nodo nodo : nodo_actual.getSiguientesNodos()) {
            String tipo_nodo = nodo.getClass().getSimpleName().substring(4);
            System.out.println(" [ID] " + nodo.getID() + " - [Tipo de quest] " + tipo_nodo);
        }

    }

    void avanzar(Jugador jugador) {
        System.out.println();
        System.out.println("---- Siguientes quest desde la quest actual! ----");
    
        Integer numero = 0;
        for (Nodo nodo : nodo_actual.getSiguientesNodos()) {
            String tipo_nodo = nodo.getClass().getSimpleName().substring(4);
            System.out.println(++numero + ".- [ID] " + nodo.getID() + " - [Tipo de quest] " + tipo_nodo);
        }

        // Le aseguro que acá no cierro el scanner porque lo hago en el main; 
        // a pesar de que no se cierre en la clase, cerrar 2 veces un scanner con stream
        // de System.in provoca un error, por que se cierra dos veces el input stream
        Scanner sc = new Scanner(System.in);
        System.out.print("Elija el numero del nodo que quiere elegir: ");
        Integer opcion = sc.nextInt();        

        if (opcion > numero || opcion < 0) {
            System.out.println(" Opción no válida, no se tomará en cuenta la decisión");
            return;
        }

        System.out.println("Cambiando de quest...");
        nodo_actual = nodo_actual.getSiguientesNodos().get(opcion-1);
        nodo_actual.interactuar(jugador);
    }

    // Getters;
    Integer getProfundidad() {
        return this.profundidad;
    }

    NodoInicial getNodoInicial() {
        return this.nodo_inicial;
    }

    Nodo getNodoActual() {
        return this.nodo_actual;
    }

    // Setters
    void setProfundidad(Integer profundidad) {
        this.profundidad = profundidad;
    }

    void setNodoInicial(NodoInicial nodo_inicial) {
        this.nodo_inicial = nodo_inicial;
    }

    void setNodoActual(Nodo nodo_actual) {
        this.nodo_actual = nodo_actual;
    }

}
