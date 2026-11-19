import java.util.HashMap;
import java.util.Map;

class Nodo {
    int expediente;
    String nombre;
    Nodo izquierda;
    Nodo derecha;
    public Nodo(int expediente, String nombre) {
        this.expediente = expediente;
        this.nombre = nombre;
        this.izquierda = null;
        this.derecha = null;
    }
}
class ArbolBinario {
    Nodo raiz;
    public ArbolBinario() {
        this.raiz = null;
    }
    public void insertar(int expediente, String nombre) {
        raiz = insertarRecursivo(raiz, expediente, nombre);
    }
    private Nodo insertarRecursivo(Nodo nodo, int expediente, String nombre) {
        if (nodo == null) {
            return new Nodo(expediente, nombre);
        }
        if (expediente < nodo.expediente) {
            nodo.izquierda = insertarRecursivo(nodo.izquierda, expediente, nombre);
        } else if (expediente > nodo.expediente) {
            nodo.derecha = insertarRecursivo(nodo.derecha, expediente, nombre);
        }
        return nodo;
    }
    public void preOrden() {
        preOrdenRecursivo(raiz);
    }

    private void preOrdenRecursivo(Nodo nodo) {
        if (nodo != null) {
            System.out.print(nodo.expediente + " " + nodo.nombre + " ");
            preOrdenRecursivo(nodo.izquierda);
            preOrdenRecursivo(nodo.derecha);
        }
    }
    public void inOrden() {
        inOrdenRecursivo(raiz);
    }
    private void inOrdenRecursivo(Nodo nodo) {
        if (nodo != null) {
            inOrdenRecursivo(nodo.izquierda);
            System.out.print(nodo.expediente + " " + nodo.nombre + " ");
            inOrdenRecursivo(nodo.derecha);
        }
    }
}