package newpackage;
import java.util.LinkedList;

public class GestionCola {
    private LinkedList<Cliente> cola;

    public GestionCola() {
        this.cola = new LinkedList<>();
    }

    public void agregarCliente(Cliente cliente) {
        cola.add(cliente);
    }

    public Cliente quitarCliente() {
        return cola.poll();
    }

    public boolean estaVacia() {
        return cola.isEmpty();
    }
}