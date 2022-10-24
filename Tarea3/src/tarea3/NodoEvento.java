package tarea3;

public class NodoEvento extends Nodo {
    private String descripcion, alternativa1, alternativa2;
    private Item resultado1, resultado2;
    
    public NodoEvento(Integer id) {
        super(id);
        inicializarEvento();
    }

    private void inicializarEvento() {
        
    }

    void interactuar(Jugador jugador) {

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
