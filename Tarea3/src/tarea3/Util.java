package tarea3;

public class Util {
    public static Integer getRandomNumber(Integer min, Integer max) {
        Integer random = (int) ((Math.random() * (max - min)) + min);
        return random;
    }
}
