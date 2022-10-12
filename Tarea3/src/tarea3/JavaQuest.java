package tarea3;

import java.util.Scanner;

class JavaQuest {
    public static void main(String[] args) {
        System.out.println("------------------------");
        System.out.println(" Bienvenido a JavaQuest ");
        System.out.println("------------------------");

        Scanner sc = new Scanner(System.in);

        // Nombre del jugador
        System.out.print("Escriba el nombre de su personaje: ");
        String nombre_jugador = sc.nextLine();
        Jugador jugador = new Jugador(nombre_jugador);
        
        Mapa mapa = new Mapa(5);


        sc.close();
    }
}