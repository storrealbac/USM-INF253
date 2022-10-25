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
        this.precio = precio;
        this.recuperar_hp = recuperar_hp;
        this.aumentar_hp_total = aumentar_hp_total;
        this.aumentar_danio = aumentar_danio;
        this.aumentar_defensa = aumentar_defensa;
    }

    void getStats() {
        System.out.println("  HP:" + getRecuperarHP());
        System.out.println("  Aumento de HP:" + getAumentarHPTotal());
        System.out.println("  Aumento de DMG:" + getAumentarDanio());
        System.out.println("  Aumento de defensa:" + getAumentarDefensa());
    }

    void aplicar(Jugador jugador) {
        Integer vida_jugador        = jugador.getHPActual();
        Integer vida_total_jugador  = jugador.getHPTotal();
        Integer dmg_jugador         = jugador.getDanio();
        Integer defensa_jugador     = jugador.getDefensa();

        jugador.setDanio(dmg_jugador + this.aumentar_danio);
        jugador.setDefensa(defensa_jugador + this.aumentar_defensa);
        jugador.setHPTotal(vida_total_jugador + this.aumentar_hp_total);
        jugador.setHPActual(Math.min(vida_jugador + this.recuperar_hp, jugador.getHPTotal()));

        // Se agrega el item
        jugador.agregarItem(this);
        System.out.println(" Se han aplicado todos los efectos en el jugador ");
    }

    public static Item generarItemAleatorio() {
        Integer precio, recuperar_hp, aumentar_hp_total, aumentar_danio, aumentar_defensa;
        precio = Util.getRandomNumber(45, 100);
        recuperar_hp = Util.getRandomNumber(0, 10);
        aumentar_danio = Util.getRandomNumber(0, 10);
        aumentar_hp_total = Util.getRandomNumber(0, 10);
        aumentar_defensa = Util.getRandomNumber(0, 10);

        Item nuevo_item = new Item(precio, recuperar_hp, aumentar_hp_total, aumentar_danio, aumentar_defensa);
        return nuevo_item;
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
