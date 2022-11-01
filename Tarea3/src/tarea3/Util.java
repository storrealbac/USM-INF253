package tarea3;

// Cree esta clase para facilitar el trabajo
public class Util {
    /**
     * Me da un numero aleatorio entre el minimo y el maximo
     * @param min: Numero minimo
     * @param max: Numero maximo
     * @return Integer: Un numero dentro del intervalo
     */
    public static Integer getRandomNumber(Integer min, Integer max) {
        Integer random = (int) ((Math.random() * (max - min)) + min);
        return random;
    }
}
