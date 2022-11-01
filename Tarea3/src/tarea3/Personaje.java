package tarea3;

public class Personaje {
    
    private String nombre;
    private Integer dinero, hp_actual, hp_total, danio, defensa;

    // Constructor vacio
    public Personaje() {}

    // Constructor para crear un personaje con sus parametros
    public Personaje(Integer dinero, Integer hp_actual, Integer hp_total, Integer danio, Integer defensa) {
        this.dinero = dinero;
        this.hp_actual = hp_actual;
        this.hp_total = hp_total;
        this.danio = danio;
        this.defensa = defensa;
    }

    // Generar un personaje random
    public Personaje(boolean random_stadistics) {
        if (!random_stadistics)
            return;

        this.dinero = Util.getRandomNumber(3, 10);
        this.hp_actual = Util.getRandomNumber(10, 15);
        this.hp_total = hp_actual;
        this.danio = Util.getRandomNumber(5, 10);
        this.defensa = Util.getRandomNumber(0, 2);
    }

    /**
     *  Ver la vida del personaje
     *  
     *  @return void
    */
    void verVida() {
        System.out.println("HP: " + this.hp_actual + "/" + this.hp_total);
    }

    /**
     * El this.personaje le pegara a personaje del parametro
     * El del parametro, hara lo mismo.
     * 
     * @param personaje: Personaje al que le pegaras
     *
     * 
     * @return void
     */
    public void pegar(Personaje personaje) {
        Integer dmg_causable = Math.max(this.getDanio()-personaje.getDefensa(), 0);
        personaje.setHPActual(Math.max(personaje.getHPActual()-dmg_causable, 0));
     }


    /**
     * Simular un combate
     * 
     * @param jugador: Personaje jugador
     * @param enemigo: Personaje enemigo
     * @param turnoJugador: Si le toca al jugador o no
     */
    private void simulacionCombate(Personaje jugador, Personaje enemigo, boolean turnoJugador) {
        // Combatiendo
        while (true) {
            System.out.println("Combatiendo...");

            System.out.print("Vida jugador: ");
            jugador.verVida();

            System.out.print("Vida enemigo: ");
            enemigo.verVida();

            if (turnoJugador)
                jugador.pegar(enemigo);
            else
                enemigo.pegar(jugador);

            turnoJugador = !turnoJugador;
            // Si murio el jugador
            if (jugador.getHPActual() == 0) {
                System.out.println("Perdiste! :(");
                System.exit(0);
            }
            // Si murio el enemigo
            else if (enemigo.getHPActual() == 0) {
                System.out.println("Le ganaste al enemigo!");
                // Dandole el dinero al jugador
                jugador.setDinero( jugador.getDinero() + enemigo.getDinero());
                System.out.println("Ganaste " + enemigo.getDinero() + " monedas!");
                break;
            }
        }
    }

    /**
     * Elige a un personaje random para comenzar a atacar y se simula el combate
     * @param personaje: Personaje con el que se peleara
     */
    public void combate(Personaje personaje) {

        // De forma aleatoria, se iran pegando
        Integer random = Util.getRandomNumber(1, 2);
        boolean turnoJugador;
        if (random == 1) turnoJugador = true;
        else turnoJugador = false;

        simulacionCombate(this, personaje, turnoJugador);
    }

    // Todos los getters
    String getNombre() {
        return this.nombre;
    }

    Integer getDinero() {
        return this.dinero;
    }

    Integer getHPActual() {
        return this.hp_actual;
    }

    Integer getHPTotal() {
        return this.hp_total;
    }

    Integer getDanio() {
        return this.danio;
    }

    Integer getDefensa() {
        return this.defensa;
    }

    // Todos los setters
    void setNombre(String nombre) {
        this.nombre = nombre;
    }

    void setDinero(Integer dinero) {
        this.dinero = dinero;
    }

    void setHPActual(Integer hp_actual) {
        this.hp_actual = hp_actual;
    }

    void setHPTotal(Integer hp_total) {
        this.hp_total = hp_total;
    }

    void setDanio(Integer danio) {
        this.danio = danio;
    }

    void setDefensa(Integer defensa) {
        this.defensa = defensa;
    }

}
