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

    
    public void insertarClienteNormal(Cliente cliente) {
        cola.addLast(cliente);
    }

    // Método para insertar cliente preferencial según la regla: 1 por cada 5 normales
    public void insertarClientePreferencial(Cliente cliente) {
        int normalesContados = 0;
        for (int i = 0; i < cola.size(); i++) {
            if (!cola.get(i).isPreferencial()) {
                normalesContados++;
            }
            if (normalesContados == 5) {
                cola.add(i + 1, cliente); 
                return;
            }
        }
        cola.addLast(cliente); 
    }

    // Quita y retorna el primer cliente de la cola
    public Cliente quitarCliente() {
        return cola.poll();
    }

    // Verifica si la cola está vacía
    public boolean estaVacia() {
        return cola.isEmpty();
    }

    // Método extra útil para mostrar el estado de la cola 
    public void mostrarCola() {
        for (Cliente c : cola) {
            System.out.print(c.getId() + (c.isPreferencial() ? "(P)" : "") + " / ");
        }
        System.out.println();
    }
}