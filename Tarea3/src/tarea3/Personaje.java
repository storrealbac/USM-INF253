package tarea3;

public class Personaje {
    
    private String nombre;
    private Integer dinero, hp_actual, hp_total, danio, defensa;

    public Personaje() {

    }

    public Personaje(Integer dinero, Integer hp_actual, Integer hp_total, Integer danio, Integer defensa) {
        this.dinero = dinero;
        this.hp_actual = hp_actual;
        this.hp_total = hp_total;
        this.danio = danio;
        this.defensa = defensa;
    }

    //random personaje
    public Personaje(boolean random_stadistics) {
        if (!random_stadistics)
            return;

        this.dinero = Util.getRandomNumber(3, 10);
        this.hp_actual = Util.getRandomNumber(10, 15);
        this.hp_total = hp_actual;
        this.danio = Util.getRandomNumber(5, 10);
        this.defensa = Util.getRandomNumber(0, 2);
    }

    void verVida() {
        System.out.println("HP: " + this.hp_actual + "/" + this.hp_total);
    }

    void combate(Personaje personaje) {
        
        // primero ataca el personaje del .this (teniendo cuidado que no se vaya a valores negativos)
        Integer dmg_causable = Math.max(this.getDanio()-personaje.getDefensa(), 0);
        personaje.setHPActual(Math.max(personaje.getHPActual()-dmg_causable, 0));

        // si no tiene vida, no nos interesa que el enemigo haga dmg
        if (personaje.getHPActual() == 0)
            return;

        Integer dmg_causado = Math.max(personaje.getDanio()-this.getDefensa(), 0);
        this.setHPActual(Math.max(this.getHPActual()-dmg_causado, 0));
        
        // en la simulacion del combate sabremos, quien gano
     }

    // Getters
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

    // Setters
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
