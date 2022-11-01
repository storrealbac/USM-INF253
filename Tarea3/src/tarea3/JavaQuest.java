package tarea3;

import java.util.Scanner;

class JavaQuest {

    public static void main(String[] args) {
        System.out.println("------------------------");
        System.out.println(" Bienvenido a JavaQuest y las aventuras del Kiwi");
        System.out.println("------------------------");

        Scanner sc = new Scanner(System.in);

        // Nombre del jugador
        System.out.print("Escriba el nombre de su personaje: ");
        String nombre_jugador = sc.nextLine();
        Jugador jugador = new Jugador(nombre_jugador);

        System.out.print("Elige el coeficiente de profundidad: ");
        Integer profundidad = sc.nextInt();
        Mapa mapa = new Mapa(profundidad);

        // Interactuar con el inicial
        mapa.getNodoActual().interactuar(jugador);


        // Ciclo de juego
        while (true) {
            
            System.out.println("---> Opciones disponibles: <---");
            System.out.println(" 1.- Ver mapa");
            System.out.println(" 2.- Ver estadisticas");
            System.out.println(" 3.- Ver items");
            System.out.println(" 4.- Avanzar");
            System.out.println(" 5.- Terminar juego");

            System.out.print("Escriba la opción: ");
            Integer opcion = sc.nextInt();

            boolean termino_juego = false;

            switch(opcion) {
                // Ver mapa
                case 1:
                    mapa.verMapa();
                    break;

                // Ver estadisticas
                case 2:
                    jugador.verEstado();
                    break;

                // Ver los items aplicados
                case 3:
                    jugador.verItems();
                    break;
                
                // Avanzar en el mapa
                case 4:
                    mapa.avanzar(jugador);
                    break;

                // Terminar el juego
                case 5:
                    termino_juego = true;
                    break;

                // Volver a intentarlo
                default:
                    System.out.println("Opción no valida, vuelva a intentarlo");
                    break;
            }

            if (termino_juego) break;
            System.out.println();
        }

        sc.close();
    }
}