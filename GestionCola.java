import java.util.ArrayList;

public class GestionCola {
    private ArrayList<Cliente> cola;

    public GestionCola() {
        cola = new ArrayList<>();
    }

    public void insertarCliente(Cliente nuevo) {
        if (!nuevo.isPreferencial()) {
            // Cliente normal se agrega al final
            cola.add(nuevo);
        } else {
            // Cliente preferencial: buscar si puede insertarse después de 5 normales
            int normalesContados = 0;

            for (int i = 0; i < cola.size(); i++) {
                if (!cola.get(i).isPreferencial()) {
                    normalesContados++;
                }

                if (normalesContados == 5) {
                    cola.add(i + 1, nuevo);
                    return;
                }
            }

            // Si no hay 5 normales, se agrega al final
            cola.add(nuevo);
        }
    }

    public void mostrarCola() {
        System.out.println("Cola actual:");
        for (Cliente c : cola) {
            String tipo = c.isPreferencial() ? " (P)" : "";
            System.out.println(c.getCodigo() + tipo);
        }
    }
}
