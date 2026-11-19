public class Grafos {
    static final String CARPETA = "C:/Users/santi/OneDrive/Escritorio/Universidad/Materias/Fundamentos de la Computacion 3/grafos/";
//C:\Users\santi\OneDrive\Escritorio\Universidad\Materias\Fundamentos de la Computacion 3\grafos\grafo_salon.txt
    public static void main(String[] args) {
        System.setOut(new java.io.PrintStream(System.out, true, java.nio.charset.StandardCharsets.UTF_8));
        System.out.println("Carpeta de trabajo: " + new java.io.File(".").getAbsolutePath());
        String[] archivos = { "grafo_salon.txt", "grafo_salon2.txt", "grafo_salon3.txt" };
        // {inicio, fin} de prueba para cada archivo (viajes entre varios nodos)
        String[][] viajes = {
            { "Arcangel", "Sergio" },
            { "Sebastian", "Arcangel" },
            { "Angel", "Yael" }
        };

        for (int k = 0; k < archivos.length; k++) {
            String texto = cargaArchivo(CARPETA + archivos[k]);
            if (texto.isEmpty()) {
                continue;
            }
            String ini = viajes[k][0];
            String fin = viajes[k][1];
            System.out.println("\n==================== " + archivos[k] + " ====================");

            System.out.println("--- GrafoArrays ---");
            GrafoArrays grafo = new GrafoArrays(texto);
            grafo.display();
            grafo.DFS(ini, fin);
            grafo.BFS(ini, fin);
            System.out.println(grafo.caminoMasCorto(ini, fin));

            System.out.println("\n--- GrafoListas ---");
            GrafoListas grafoListas = new GrafoListas(texto);
            grafoListas.display();
            grafoListas.DFS(ini, fin);
            grafoListas.BFS(ini, fin);
            System.out.println(grafoListas.caminoMasCorto(ini, fin));
            System.out.println(grafoListas.caminoMasCorto(fin, ini)); // viaje de regreso
            System.out.println(grafoListas.caminoMasCorto(ini, "NoExiste")); // caso de error
        }
    }

    public static String cargaArchivo(String nombre) {
    String[] carpetas = { "", "grafos/", "src/", "grafos/src/", "../" };
    for (String carpeta : carpetas) {
        java.io.File archivo = new java.io.File(carpeta + nombre);
        if (archivo.exists()) {
            StringBuilder contenido = new StringBuilder();
            try (java.util.Scanner scanner = new java.util.Scanner(archivo)) {
                while (scanner.hasNextLine()) {
                    contenido.append(scanner.nextLine()).append("\n");
                }
            } catch (java.io.FileNotFoundException e) {
                // no debería pasar, ya comprobamos que existe
            }
            return contenido.toString();
        }
    }
    System.out.println("Archivo no encontrado: " + nombre);
    return "";
}
}