package newpackage;

import java.util.Random;

/**
 * Representa una caja de atención en el banco.
 * Soporta habilitación/deshabilitación, inicio de atención y finalización manual.
 */
public class Caja {
    private String nombre;
    private boolean habilitada;
    private boolean disponible;
    private Cliente clienteActual;

    public Caja(String nombre) {
        this.nombre = nombre;
        this.habilitada = true;
        this.disponible = true;
        this.clienteActual = null;
    }

    /**
     * Llama a un cliente y lo asigna si responde.
     * No termina la atención automáticamente.
     */
    public void llamarCliente(Cliente cliente) {
        System.out.println( nombre + " llama al cliente: " + cliente);
        if (clienteResponde()) {
            System.out.println("Cliente " + cliente.getId() + " respondió. Atención iniciada.");
            disponible = false;
            clienteActual = cliente;
        } else {
            System.out.println(" Cliente " + cliente.getId() + " no respondió. Eliminado de la cola.");
        }
    }

    /**
     * Finaliza manualmente la atención en la caja.
     */
    public void finalizarAtencion() {
        if (clienteActual != null) {
            System.out.println(" Atención finalizada para " + clienteActual.getId() + " en " + nombre);
            clienteActual = null;
            disponible = true;
        } else {
            System.out.println(" No hay ningún cliente siendo atendido en " + nombre);
        }
    }

    private boolean clienteResponde() {
        return new Random().nextBoolean();
    }

    public boolean estaDisponible() { return disponible; }
    public boolean estaHabilitada() { return habilitada; }
    public Cliente getClienteActual() { return clienteActual; }

    public void habilitar() {
        this.habilitada = true;
        System.out.println( nombre + " ha sido habilitada.");
    }

    public void deshabilitar() {
        if (!disponible && clienteActual != null) {
            System.out.println("Cliente " + clienteActual.getId() + " está en espera. Cerrando caja.");
        }
        this.habilitada = false;
    }

    @Override
    public String toString() {
        if (!habilitada) {
            return nombre + " [Deshabilitada]";
        } else if (!disponible && clienteActual != null) {
            return nombre + " [Ocupada] atendiendo a " +
                   clienteActual.getId() +
                   (clienteActual.isPreferencial() ? " [P]" : "") +
                   " (" + clienteActual.getEdad() + " años)";
        } else {
            return nombre + " [Libre]";
        }
    }
    public String getNombre() {
    return nombre;
}

}
