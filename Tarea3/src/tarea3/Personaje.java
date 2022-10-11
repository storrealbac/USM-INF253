package tarea3;

public class Personaje {
    
    private String nombre;
    private Integer dinero, hp_actual, hp_total, danio, defensa;

    void combate(Personaje personaje) {
        
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
