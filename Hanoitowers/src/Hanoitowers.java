import java.util.Scanner;
import java.util.Stack;

public class HanoiTowers {
    static Scanner sc = new Scanner(System.in);
    static int numDiscos = 3;
    static int movimientos = 0; // contador de movimientos válidos
    @SuppressWarnings("unchecked")
    static Stack<Integer>[] torres = new Stack[3]; // Arreglo de pilas para representar las torres

    public static void main(String[] args) {
        menuPrincipal();
    }

    static int leerEntero() {
        while (!sc.hasNextInt()) {
            System.out.print("Ingresa un numero valido: ");
            sc.next();
        }
        return sc.nextInt();
    }

    public static void menuPrincipal() {
        int opcion;
        do {
            System.out.println("\n===== TORRES DE HANOI =====");
            System.out.println("Numero de discos actual: " + numDiscos);
            System.out.println("1. Elegir numero de discos (3-8)");
            System.out.println("2. Jugar manualmente");
            System.out.println("3. Mostrar solucion automatica");
            System.out.println("4. Salir");
            System.out.print("Elige una opcion: ");
            opcion = leerEntero();
            switch (opcion) {
                case 1:
                    elegirNumeroDiscos();
                    break;
                case 2:
                    jugarManual();
                    break;
                case 3:
                    mostrarSolucion();
                    break;
                case 4:
                    System.out.println("Hasta luego.");
                    break;
                default:
                    System.out.println("Opción invalida.");
            }
        } while (opcion != 4);
    }

    static void inicializaTorres() {
        for (int i = 0; i < 3; i++) {
            torres[i] = new Stack<>();
        }
        // Inicializa la torre A con los discos
        for (int i = numDiscos; i >= 1; i--) {
            torres[0].push(i);
        }
        movimientos = 0;
    }

    static void despliegaTorre(int torre) {
        System.out.print("Torre " + (char) ('A' + torre) + ": ");
        for (int disco : torres[torre]) {
            System.out.print(disco + " ");
        }
        System.out.println();
    }

    static void despliegaTorres() {
        for (int i = 0; i < 3; i++) {
            despliegaTorre(i);
        }
    }

    public static void elegirNumeroDiscos() {
        int n;
        do {
            System.out.print("Ingresa el numero de discos (3-8): ");
            n = leerEntero();
            if (n < 3 || n > 8) {
                System.out.println("Numero invalido. Debe estar entre 3 y 8.");
            }
        } while (n < 3 || n > 8);
        numDiscos = n;
    }

    public static void jugarManual() {
        inicializaTorres();
        while (numDiscos > torres[2].size()) {
            despliegaTorres();
            String origen = eligeTorre("Elige la torre de origen (A, B, C) o S para salir: ");
            if (origen.equals("S")) {
                System.out.println("Juego cancelado.");
                return;
            }
            String destino = eligeTorre("Elige la torre de destino (A, B, C) o S para salir: ");
            if (destino.equals("S")) {
                System.out.println("Juego cancelado.");
                return;
            }
            int torreOrigen = origen.charAt(0) - 'A';
            int torreDestino = destino.charAt(0) - 'A';
            if (torres[torreOrigen].isEmpty()) {
                System.out.println("La torre de origen está vacia. Intenta de nuevo.");
                continue;
            }
            if (torreOrigen == torreDestino) {
                System.out.println("La torre de origen y destino son las mismas. Intenta de nuevo.");
                continue;
            }
            moverDisco(torreOrigen, torreDestino);
        }
        despliegaTorres();
        System.out.println("¡Felicidades! Has completado el juego en " + movimientos
                + " movimientos (el mínimo posible es " + movimientosMinimos() + ").");
    }

    static int movimientosMinimos() {
        return (1 << numDiscos) - 1; // 2^n - 1
    }

    public static boolean moverDisco(int origen, int destino) {
        if (torres[origen].isEmpty()) {
            System.out.println("No hay discos en la torre de origen.");
            return false;
        }
        if (!torres[destino].isEmpty() && torres[destino].peek() < torres[origen].peek()) {
            System.out.println("Movimiento invalido. No puedes colocar un disco mas grande sobre uno mas pequeño.");
            return false;
        }
        int disco = torres[origen].pop();
        torres[destino].push(disco);
        movimientos++;
        System.out.println("Moviste el disco " + disco + " de la torre " + (char) ('A' + origen)
                + " a la torre " + (char) ('A' + destino) + ".");
        return true;
    }

    /** Pide una torre. Devuelve "A", "B", "C" o "S" (salir). */
    public static String eligeTorre(String mensaje) {
        String torre;
        do {
            System.out.print(mensaje);
            torre = sc.next().toUpperCase();
            if (!torre.equals("A") && !torre.equals("B") && !torre.equals("C") && !torre.equals("S")) {
                System.out.println("Entrada inválida. Debe ser A, B, C o S.");
            }
        } while (!torre.equals("A") && !torre.equals("B") && !torre.equals("C") && !torre.equals("S"));
        return torre;
    }

    public static void mostrarSolucion() {
        inicializaTorres();
        System.out.println("\nEstado inicial:");
        despliegaTorres();
        resuelveHanoi(numDiscos, 0, 2, 1);
        System.out.println("\nProblema resuelto en " + movimientos + " movimientos.");
    }

    // Mueve n discos de origen a destino usando auxiliar, mostrando las torres tras cada movimiento
    static void resuelveHanoi(int n, int origen, int destino, int auxiliar) {
        if (n == 0) {
            return;
        }
        resuelveHanoi(n - 1, origen, auxiliar, destino);
        moverDisco(origen, destino);
        despliegaTorres();
        System.out.println();
        resuelveHanoi(n - 1, auxiliar, destino, origen);
    }
}