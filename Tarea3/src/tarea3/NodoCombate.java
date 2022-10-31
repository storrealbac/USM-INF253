package tarea3;

public class NodoCombate extends Nodo {
    private Personaje enemigo;

    public NodoCombate(Integer id) {
        super(id);
        enemigo = new Personaje(true);
    }

    void interactuar(Jugador jugador) {

        // Combatiendo
        while (true) {
            System.out.println("Combatiendo...");

            System.out.print("Vida jugador: ");
            jugador.verVida();

            System.out.print("Vida enemigo: ");
            enemigo.verVida();

            jugador.combate(enemigo);

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

    Personaje getEnemigo() {
        return this.enemigo;
    }
    
    void setEnemigo(Personaje enemigo) {
        this.enemigo = enemigo;
    }
}
