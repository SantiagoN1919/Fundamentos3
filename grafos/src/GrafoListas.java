import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class GrafoListas {
    private ArrayList<Nodos> nodos;

    public GrafoListas(String texto) {
        nodos = new ArrayList<>();
        creaListaVertices(texto);
        agregaAristas(texto);
    }

    /**
     * Interpreta UNA línea del archivo y devuelve {origen, destino, peso}.
     * Devuelve null si la línea no es una arista (encabezado, comentario, "}", etc.).
     * Funciona con los tres formatos: "graph G{A--B[label="1"]", "{A--B[...]" y "A -- B [label="1"];"
     * También tolera errores de escritura en la etiqueta (ej. laberl="1").
     */
    static String[] parseLinea(String linea) {
        String l = linea;
        int comentario = l.indexOf("//");
        if (comentario >= 0) {
            l = l.substring(0, comentario);
        }
        int llave = l.indexOf('{');
        if (llave >= 0) {
            l = l.substring(llave + 1); // descarta "graph G{" o "{"
        }
        l = l.replace("}", "");

        String peso = "1"; // peso por defecto
        int corchete = l.indexOf('[');
        if (corchete >= 0) {
            int fin = l.indexOf(']', corchete);
            if (fin < 0) {
                fin = l.length();
            }
            String atributo = l.substring(corchete + 1, fin);
            int igual = atributo.indexOf('=');
            if (igual >= 0) {
                peso = atributo.substring(igual + 1).replace("\"", "").trim();
            }
            l = l.substring(0, corchete);
        }
        l = l.replace(";", "").trim();

        String[] extremos = l.split("--", 2);
        if (extremos.length < 2) {
            return null;
        }
        String a = extremos[0].trim();
        String b = extremos[1].trim();
        if (a.isEmpty() || b.isEmpty()) {
            return null;
        }
        return new String[] { a, b, peso };
    }

    private void creaListaVertices(String texto) {
        for (String linea : texto.split("\\R")) {
            String[] datos = parseLinea(linea);
            if (datos != null) {
                agregarVertice(datos[0]);
                agregarVertice(datos[1]);
            }
        }
    }

    private void agregarVertice(String nombre) {
        if (obtenerNodoPorNombre(nombre) == null) {
            nodos.add(new Nodos(nombre));
        }
    }

    private void agregaAristas(String texto) {
        for (String linea : texto.split("\\R")) {
            String[] datos = parseLinea(linea);
            if (datos == null) {
                continue;
            }
            int peso = 1;
            try {
                peso = Integer.parseInt(datos[2]);
            } catch (NumberFormatException e) {
                System.out.println("Peso inválido en la línea: " + linea + " (se usa 1)");
            }
            // Usamos los nodos REALES de la lista, no copias nuevas
            Nodos origen = obtenerNodoPorNombre(datos[0]);
            Nodos destino = obtenerNodoPorNombre(datos[1]);
            if (origen != null && destino != null) {
                origen.agregarAdyacente(destino, peso);
                destino.agregarAdyacente(origen, peso); // grafo no dirigido
            }
        }
    }

    private Nodos obtenerNodoPorNombre(String nombre) {
        for (Nodos nodo : nodos) {
            if (nodo.getNombre().equals(nombre)) {
                return nodo;
            }
        }
        return null;
    }

    public void display() {
        System.out.println("Lista de adyacencia:");
        for (Nodos nodo : nodos) {
            nodo.display();
        }
    }

    public void DFS(String verticeInicial, String verticeFinal) {
        Nodos nodoInicial = obtenerNodoPorNombre(verticeInicial);
        Nodos nodoFinal = obtenerNodoPorNombre(verticeFinal);
        if (nodoInicial == null || nodoFinal == null) {
            System.out.println("Uno o ambos vértices no existen en el grafo.");
            return;
        }
        boolean[] visitado = new boolean[nodos.size()];
        System.out.println("Recorrido DFS desde " + verticeInicial + " hasta " + verticeFinal + ":");
        Stack<Nodos> pila = new Stack<>();
        pila.push(nodoInicial);
        while (!pila.isEmpty()) {
            Nodos actual = pila.pop();
            int i = nodos.indexOf(actual);
            if (visitado[i]) {
                continue;
            }
            visitado[i] = true;
            System.out.print(actual.getNombre() + " ");
            if (actual == nodoFinal) {
                System.out.println("\nSe ha alcanzado el vértice final.");
                return;
            }
            for (Nodos.Arista a : actual.getAdyacentes()) {
                if (!visitado[nodos.indexOf(a.getDestino())]) {
                    pila.push(a.getDestino());
                }
            }
        }
        System.out.println("\nNo se alcanzó el vértice final.");
    }

    /** Recorrido en anchura (por niveles) usando una cola. */
    public void BFS(String verticeInicial, String verticeFinal) {
        Nodos nodoInicial = obtenerNodoPorNombre(verticeInicial);
        Nodos nodoFinal = obtenerNodoPorNombre(verticeFinal);
        if (nodoInicial == null || nodoFinal == null) {
            System.out.println("Uno o ambos vértices no existen en el grafo.");
            return;
        }
        boolean[] visitado = new boolean[nodos.size()];
        System.out.println("Recorrido BFS desde " + verticeInicial + " hasta " + verticeFinal + ":");
        Queue<Nodos> cola = new LinkedList<>();
        cola.add(nodoInicial);
        visitado[nodos.indexOf(nodoInicial)] = true; // se marca al encolar, así no se repite
        while (!cola.isEmpty()) {
            Nodos actual = cola.poll();
            System.out.print(actual.getNombre() + " ");
            if (actual == nodoFinal) {
                System.out.println("\nSe ha alcanzado el vértice final.");
                return;
            }
            for (Nodos.Arista a : actual.getAdyacentes()) {
                int j = nodos.indexOf(a.getDestino());
                if (!visitado[j]) {
                    visitado[j] = true;
                    cola.add(a.getDestino());
                }
            }
        }
        System.out.println("\nNo se alcanzó el vértice final.");
    }

    /** Camino de costo mínimo entre dos vértices (Dijkstra sobre listas de adyacencia). */
    public String caminoMasCorto(String verticeInicial, String verticeFinal) {
        Nodos nodoInicial = obtenerNodoPorNombre(verticeInicial);
        Nodos nodoFinal = obtenerNodoPorNombre(verticeFinal);
        if (nodoInicial == null || nodoFinal == null) {
            return "Uno o ambos vértices no existen en el grafo.";
        }
        int n = nodos.size();
        int inicio = nodos.indexOf(nodoInicial);
        int destino = nodos.indexOf(nodoFinal);

        int[] distancia = new int[n];
        int[] anterior = new int[n];
        boolean[] definitivo = new boolean[n];
        Arrays.fill(distancia, Integer.MAX_VALUE);
        Arrays.fill(anterior, -1);
        distancia[inicio] = 0;

        for (int paso = 0; paso < n; paso++) {
            // Elegimos el no definitivo con menor distancia
            int actual = -1;
            for (int i = 0; i < n; i++) {
                if (!definitivo[i] && distancia[i] != Integer.MAX_VALUE
                        && (actual == -1 || distancia[i] < distancia[actual])) {
                    actual = i;
                }
            }
            if (actual == -1 || actual == destino) {
                break;
            }
            definitivo[actual] = true;

            // Relajamos las aristas de ese nodo
            for (Nodos.Arista a : nodos.get(actual).getAdyacentes()) {
                int vecino = nodos.indexOf(a.getDestino());
                if (!definitivo[vecino]) {
                    int nueva = distancia[actual] + a.getPeso();
                    if (nueva < distancia[vecino]) {
                        distancia[vecino] = nueva;
                        anterior[vecino] = actual;
                    }
                }
            }
        }

        if (distancia[destino] == Integer.MAX_VALUE) {
            return "No existe un camino entre " + verticeInicial + " y " + verticeFinal + ".";
        }
        LinkedList<String> camino = new LinkedList<>();
        for (int i = destino; i != -1; i = anterior[i]) {
            camino.addFirst(nodos.get(i).getNombre());
        }
        return "Camino más corto: " + String.join(" -> ", camino)
                + " (costo total: " + distancia[destino] + ")";
    }
}