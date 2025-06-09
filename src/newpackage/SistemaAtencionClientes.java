package newpackage;
import java.util.*;

public class SistemaAtencionClientes {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        GestionCola gestionCola = new GestionCola();
        List<Cliente> clientesAtendidos = new ArrayList<>();
        List<Cliente> cajas = new ArrayList<>();
        int numCajas = 2;

        for (int i = 0; i < numCajas; i++) {
            cajas.add(null);
        }

        int opcion;
        do {
            System.out.println("\n--- Menú Banco ---");
            System.out.println("1. Insertar cliente a la cola");
            System.out.println("2. Insertar cliente preferencial a la cola");
            for (int i = 0; i < cajas.size(); i++) {
                System.out.println((i + 3) + ". Atender siguiente cliente (Caja " + (i + 1) + ")");
            }
            System.out.println((3 + cajas.size()) + ". Agregar nueva caja de atención");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    Cliente clienteNormal = new Cliente(); // constructor vacío
                    gestionCola.insertarClienteNormal(clienteNormal);
                    break;

                case 2:
                    Cliente clientePref = new Cliente(); // constructor vacío
                    gestionCola.insertarClientePreferencial(clientePref);
                    break;

                default:
                    if (opcion >= 3 && opcion < 3 + cajas.size()) {
                        int cajaIndex = opcion - 3;
                        Cliente atendido = gestionCola.quitarCliente();
                        cajas.set(cajaIndex, atendido);
                        if (atendido != null) {
                            clientesAtendidos.add(atendido);
                        }
                    } else if (opcion == 3 + cajas.size()) {
                        cajas.add(null);
                        System.out.println("Nueva caja agregada. Total de cajas: " + cajas.size());
                    } else if (opcion != 0) {
                        System.out.println("Opción inválida.");
                    }
            }

            // Mostrar cola de clientes
            System.out.print("Cola de clientes: ");
            gestionCola.mostrarCola();

            // Mostrar estado de cajas
            for (int i = 0; i < cajas.size(); i++) {
                System.out.print("Caja " + (i + 1) + ": ");
                Cliente atendiendo = cajas.get(i);
                if (atendiendo != null) {
                    System.out.println("(Llamando a " + atendiendo.getId() + ")");
                } else {
                    System.out.println("(Libre)");
                }
            }

        } while (opcion != 0);
        sc.close();
    }
}