package tarea3;

public class NodoJefeFinal extends Nodo {
    private Personaje jefe;
    
    // Inicializa un jefe final random
    public NodoJefeFinal(Integer id) {
        super(id);

        jefe = new Personaje(true);

        jefe.setDanio(jefe.getDanio() + Util.getRandomNumber(10, 35));
        // 0 defensa, pero mucha vida
        jefe.setDefensa(0);
        jefe.setHPTotal(jefe.getHPTotal() + Util.getRandomNumber(9000, 10000));
        jefe.setHPActual(jefe.getHPTotal());
    }

    /**
     * Interactua con el nodo Jefe Final: Peleas con Kiwi
     * 
     * @param jugador: Jugador que interactuara
     * 
     * @return void
     */
    void interactuar(Jugador jugador) {
        // Combatiendo
        while (true) {
            System.out.println("Combatiendo...");

            System.out.print("Vida jugador: ");
            jugador.verVida();

            System.out.print("Vida Kiwi: ");
            jefe.verVida();

            jugador.combate(jefe);

            // Si murio el jugador
            if (jugador.getHPActual() == 0) {
                System.out.println("Perdiste contra Kiwi :(");
                System.exit(0);
            }
            // Si murio el enemigo
            else if (jefe.getHPActual() == 0) {
                System.out.println("Le ganaste a Kiwi, pasaste el ramo de LP :D!");
                System.out.println("Termino el juego!");
                System.exit(0);
            }
        }
    }   

    // Getters
    Personaje getJefe() {
        return this.jefe;
    }

    // Setters
    void setJefe(Personaje jefe) {
        this.jefe = jefe;
    }
}
