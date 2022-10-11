package tarea3;

public class Mapa {
    
    private Integer profundidad;
    private NodoInicial nodo_inicial;
    private Nodo nodo_actual;

    void verMapa() {

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
