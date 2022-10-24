package tarea3;

public class Item {
    private Integer precio, recuperar_hp, aumentar_hp_total, aumentar_danio, aumentar_defensa;

    public Item(
        Integer precio,
        Integer recuperar_hp,
        Integer aumentar_hp_total,
        Integer aumentar_danio,
        Integer aumentar_defensa
    ) {

    }

    void aplicar(Jugador jugador) {
        Integer vida_jugador        = jugador.getHPActual();
        Integer vida_total_jugador  = jugador.getHPTotal();
        Integer dmg_jugador         = jugador.getDanio();
        Integer defensa_jugador     = jugador.getDefensa();

        jugador.setDanio(dmg_jugador + this.aumentar_danio);
        jugador.setDefensa(defensa_jugador + this.aumentar_defensa);
        jugador.setHPActual(vida_jugador + this.recuperar_hp);
        jugador.setHPTotal(vida_total_jugador + this.aumentar_hp_total);

        System.out.println(" Se han aplicado todos los efectos en el jugador ");
    }

    // Getters
    Integer getPrecio() {
        return this.precio;
    }

    Integer getRecuperarHP() {
        return this.recuperar_hp;
    }

    Integer getAumentarHPTotal() {
        return this.aumentar_hp_total;
    }

    Integer getAumentarDanio() {
        return this.aumentar_danio;
    }

    Integer getAumentarDefensa() {
        return this.aumentar_defensa;
    }
}
