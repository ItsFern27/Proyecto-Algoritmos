package newpackage;

import java.util.*;

/**
 * Clase principal del sistema de turnos para bancos.
 * Administra la creación de clientes, la atención en cajas, y la interacción del usuario.
 */
public class SistemaAtencionClientes {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        GestionCola gestionCola = new GestionCola();
        List<Caja> cajas = new ArrayList<>();

        // Se agregan 2 cajas por defecto
        cajas.add(new Caja("Caja 1"));
        cajas.add(new Caja("Caja 2"));

        int opcion;
        do {
            System.out.println("\n--- Menú Banco ---");
            System.out.println("1. Generar cliente aleatorio");
            System.out.println("2. Llamar al siguiente cliente (Caja disponible y habilitada)");
            System.out.println("3. Mostrar cola y estado de cajas");

            // Alternar habilitación de cajas existentes
            for (int i = 0; i < cajas.size(); i++) {
                System.out.println((4 + i) + ". Alternar estado de " + cajas.get(i).getNombre());
            }

            // Opción para finalizar atención manual en cada caja
            for (int i = 0; i < cajas.size(); i++) {
                System.out.println((4 + cajas.size() + i) + ". Finalizar atención en " + cajas.get(i).getNombre());
            }

            // Opción para agregar una nueva caja
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    // Generación automática del cliente y su inserción en la cola
                    Cliente nuevo = new Cliente();
                    gestionCola.insertarCliente(nuevo);
                    System.out.println("Cliente insertado: " + nuevo);
                    break;

                case 2:
                    if (!gestionCola.hayClientes()) {
                        System.out.println("No hay clientes en cola.");
                        break;
                    }

                    Cliente siguiente = gestionCola.verSiguiente();
                    boolean llamado = false;

                    // Buscar la primera caja habilitada y libre
                    for (Caja caja : cajas) {
                        if (caja.estaHabilitada() && caja.estaDisponible()) {
                            gestionCola.quitarCliente(); // Lo elimina de la cola
                            caja.llamarCliente(siguiente); // Llama al cliente
                            llamado = true;
                            break;
                        }
                    }

                    if (!llamado) {
                        System.out.println("No hay cajas habilitadas y disponibles.");
                    }
                    break;

                case 3:
                    // Mostrar la cola con el estado del siguiente cliente
                    System.out.println("\nEstado de clientes en cola:");
                    List<Cliente> colaActual = gestionCola.getCola();
                    if (colaActual.isEmpty()) {
                        System.out.println("La cola está vacía.");
                    } else {
                        for (int i = 0; i < colaActual.size(); i++) {
                            Cliente c = colaActual.get(i);
                            String estado = (i == 0) ? " Próximo a ser llamado" : "";
                            System.out.println((i + 1) + ". " + c.toString() + " " + estado);
                        }
                    }

                    // Mostrar estado de todas las cajas
                    System.out.println("\nEstado de cajas:");
                    for (Caja caja : cajas) {
                        System.out.println("   " + caja);
                    }
                    break;

                default:
                    int toggleIndex = opcion - 4;

                    // Alternar habilitación de caja
                    if (toggleIndex >= 0 && toggleIndex < cajas.size()) {
                        Caja caja = cajas.get(toggleIndex);
                        if (caja.estaHabilitada()) {
                            caja.deshabilitar();
                        } else {
                            caja.habilitar();
                        }

                    // Finalizar atención manual en cajas
                    } else if (toggleIndex >= cajas.size() && toggleIndex < cajas.size() * 2) {
                        int finalizarIndex = toggleIndex - cajas.size();
                        Caja caja = cajas.get(finalizarIndex);
                        caja.finalizarAtencion();
                    } else if (opcion != 0) {
                        System.out.println("Opción inválida.");
                    }
                    break;
            }

        } while (opcion != 0);

        sc.close();
        System.out.println("Gracias por usar el sistema de atención de clientes.");
    }
}
