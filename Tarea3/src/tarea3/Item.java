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
