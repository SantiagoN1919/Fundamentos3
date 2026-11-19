class NodoArbol {
    int expediente;
    String nombre;
    NodoArbol izquierda;
    NodoArbol derecha;

    public NodoArbol(int expediente, String nombre) {
        this.expediente = expediente;
        this.nombre = nombre;
    }
}

class ArbolBinario {
    NodoArbol raiz;

    public void insertar(int expediente, String nombre) {
        raiz = insertarRecursivo(raiz, expediente, nombre);
    }

    private NodoArbol insertarRecursivo(NodoArbol nodo, int expediente, String nombre) {
        if (nodo == null) {
            return new NodoArbol(expediente, nombre);
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

    private void preOrdenRecursivo(NodoArbol nodo) {
        if (nodo != null) {
            System.out.print(nodo.expediente + " " + nodo.nombre + " ");
            preOrdenRecursivo(nodo.izquierda);
            preOrdenRecursivo(nodo.derecha);
        }
    }

    public void inOrden() {
        inOrdenRecursivo(raiz);
    }

    private void inOrdenRecursivo(NodoArbol nodo) {
        if (nodo != null) {
            inOrdenRecursivo(nodo.izquierda);
            System.out.print(nodo.expediente + " " + nodo.nombre + " ");
            inOrdenRecursivo(nodo.derecha);
        }
    }
}

public class ArbolBienNombres {
    public static void main(String[] args) {
        ArbolBinario arbol = new ArbolBinario();
        arbol.insertar(50, "Ana");
        arbol.insertar(30, "Luis");
        arbol.insertar(70, "Marta");
        arbol.insertar(20, "Pedro");

        System.out.print("PreOrden: ");
        arbol.preOrden();
        System.out.print("\nInOrden: ");
        arbol.inOrden();
        System.out.println();
    }
}