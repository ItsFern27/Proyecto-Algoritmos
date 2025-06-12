package newpackage;

import java.util.*;

/**
 * Gestiona la cola de clientes, permitiendo insertar normales y preferenciales
 * cumpliendo la regla de 1 preferencial por cada 5 normales.
 */
public class GestionCola {
    private List<Cliente> cola = new ArrayList<>();
    private int contadorNormales = 0;

    public void insertarCliente(Cliente cliente) {
        if (cliente.isPreferencial()) {
            insertarClientePreferencial(cliente);
        } else {
            insertarClienteNormal(cliente);
        }
    }

    public void insertarClienteNormal(Cliente cliente) {
        cola.add(cliente);
        contadorNormales++;
    }

    public void insertarClientePreferencial(Cliente cliente) {
        if (contadorNormales >= 5) {
            // Busca el 5to cliente normal más reciente para insertar después de él
            int normalesContados = 0;
            for (int i = 0; i < cola.size(); i++) {
                if (!cola.get(i).isPreferencial()) {
                    normalesContados++;
                    if (normalesContados == 5) {
                        cola.add(i + 1, cliente); // Inserta después del 5to normal
                        contadorNormales = 0;
                        return;
                    }
                }
            }
        }

        // Si aún no hay suficientes normales, se inserta al final
        cola.add(cliente);
    }

    public Cliente quitarCliente() {
        return cola.isEmpty() ? null : cola.remove(0);
    }

    public Cliente verSiguiente() {
        return cola.isEmpty() ? null : cola.get(0);
    }

    /**
     * Muestra todos los clientes en la cola, marcando quién es el próximo
     * y si es preferencial.
     */
    public void mostrarCola() {
        if (cola.isEmpty()) {
            System.out.println("La cola está vacía.");
        } else {
            for (int i = 0; i < cola.size(); i++) {
                Cliente c = cola.get(i);
                System.out.println((i == 0 ? "  " : "   ") + (i + 1) + ". " + c.toString());
            }
        }
    }

    public boolean hayClientes() {
        return !cola.isEmpty();
    }

    public List<Cliente> getCola() {
    return cola;
}

}
