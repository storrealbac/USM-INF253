package tarea3;

import java.util.Random;
import java.util.SortedSet;

import GraphGenerator.GraphGenerator;
import GraphGenerator.Edge;


public class Mapa {
    
    private Integer profundidad;
    private NodoInicial nodo_inicial;
    private Nodo nodo_actual;

    public Mapa(Integer profundidad) {
        this.profundidad = profundidad;
        nodo_inicial = new NodoInicial(0);
        nodo_actual = nodo_inicial;
        
        generarMapa(nodo_inicial);
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

    // ver los siguientes nodos
    void verMapa() {
        System.out.println("Posibles siguientes caminos: ");
        for (Nodo n : nodo_actual.getSiguientesNodos()) {
            System.out.println("    [ID] " + n.getID() + " - [Tipo de nodo] " + n.getClass().getSimpleName());
        }
    }

    void avanzar() {
        
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
