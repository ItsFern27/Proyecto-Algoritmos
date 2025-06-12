package newpackage;

import java.util.*;

/**
 * Gestiona la cola de clientes, permitiendo insertar normales y preferenciales
 * cumpliendo la regla de 1 preferencial por cada 5 normales.
 */
public class GestionCola {
    private LinkedList<Cliente> cola = new LinkedList<>();
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
    }

    public void insertarClientePreferencial(Cliente cliente) {
        ListIterator<Cliente> it = cola.listIterator();
        int consecutivosNormales = 0;

        while (it.hasNext()) {
            Cliente actual = it.next();

            if (actual.isPreferencial()) {
                consecutivosNormales = 0; // se interrumpe la cadena
                continue;
            }

            consecutivosNormales++;

            if (consecutivosNormales == 5) {
                // Verificamos el siguiente si existe
                if (it.hasNext()) {
                    Cliente siguiente = it.next();
                    if (siguiente.isPreferencial()) {
                        // Hay un preferencial después: reiniciar
                        consecutivosNormales = 0;
                        continue;
                    } else {
                        // Volvemos una posición atrás para insertar después del 5º normal
                        it.previous(); // retrocedemos para no saltar el siguiente cliente
                        it.add(cliente);
                        return;
                    }
                } else {
                    // No hay más clientes, insertamos al final
                    it.add(cliente);
                    return;
                }
            }
        }

        // Si no se encontró espacio válido, se inserta al final
        cola.add(cliente);
    }

    public Cliente quitarCliente() {
        return cola.isEmpty() ? null : cola.poll();
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
