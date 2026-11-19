import java.util.ArrayList;

public class Nodos {

    /** Una arista guarda el nodo destino y SU propio peso. */
    public static class Arista {
        private final Nodos destino;
        private int peso;

        public Arista(Nodos destino, int peso) {
            this.destino = destino;
            this.peso = peso;
        }

        public Nodos getDestino() {
            return destino;
        }

        public int getPeso() {
            return peso;
        }

        public void setPeso(int peso) {
            this.peso = peso;
        }
    }

    private final String nombre;
    private final ArrayList<Arista> adyacentes;
    private boolean visitado;

    public Nodos(String nombre) {
        this.nombre = nombre;
        this.visitado = false;
        this.adyacentes = new ArrayList<>();
    }

    public void agregarAdyacente(Nodos destino, int peso) {
        // Si la arista ya existe, solo actualizamos el peso
        for (Arista a : adyacentes) {
            if (a.getDestino() == destino) {
                a.setPeso(peso);
                return;
            }
        }
        adyacentes.add(new Arista(destino, peso));
    }

    public void display() {
        System.out.print(nombre + " | Adyacentes: ");
        for (Arista a : adyacentes) {
            System.out.print(a.getDestino().getNombre() + ": " + a.getPeso() + "  ");
        }
        System.out.println();
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Arista> getAdyacentes() {
        return adyacentes;
    }

    public boolean isVisitado() {
        return visitado;
    }

    public void setVisitado(boolean visitado) {
        this.visitado = visitado;
    }
}