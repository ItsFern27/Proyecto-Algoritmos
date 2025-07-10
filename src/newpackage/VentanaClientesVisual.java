package newpackage;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.HashMap;

public class VentanaClientesVisual extends JFrame {
    private GestionCola gestionCola;
    private JPanel[] bloques;
    private JLabel[] etiquetasId;
    private JLabel[] etiquetasEstado;
    private HashMap<String, String> estadoClientes = new HashMap<>();
    private Cliente[] clientesMostrados = new Cliente[6];


    public VentanaClientesVisual(GestionCola gestionCola) {
        this.gestionCola = gestionCola;
        bloques = new JPanel[6];
        etiquetasId = new JLabel[6];
        etiquetasEstado = new JLabel[6];

        setTitle("Vista Visual de Clientes");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panel principal vertical (espacio superior + bloques)
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());

        // Espacio rectangular encima (puedes usarlo luego como quieras)
        JPanel panelSuperior = new JPanel();
        panelSuperior.setPreferredSize(new Dimension(800, 400));
        panelSuperior.setBackground(new Color(220, 220, 220));
        panelSuperior.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);

        // Panel central de bloques (2 filas de 3)
        JPanel panelBloques = new JPanel(new GridLayout(2, 3, 10, 10));
        panelBloques.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (int i = 0; i < 6; i++) {
            bloques[i] = new JPanel();
            bloques[i].setLayout(new BoxLayout(bloques[i], BoxLayout.Y_AXIS));
            bloques[i].setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            bloques[i].setBackground(Color.WHITE);

            etiquetasId[i] = new JLabel("", SwingConstants.CENTER);
            etiquetasEstado[i] = new JLabel("", SwingConstants.CENTER);

            etiquetasId[i].setAlignmentX(Component.CENTER_ALIGNMENT);
            etiquetasEstado[i].setAlignmentX(Component.CENTER_ALIGNMENT);

            etiquetasId[i].setFont(new Font("Arial", Font.BOLD, 18));
            etiquetasEstado[i].setFont(new Font("Arial", Font.PLAIN, 14));

            bloques[i].add(Box.createVerticalGlue());
            bloques[i].add(etiquetasId[i]);
            bloques[i].add(Box.createVerticalStrut(5));
            bloques[i].add(etiquetasEstado[i]);
            bloques[i].add(Box.createVerticalGlue());

            panelBloques.add(bloques[i]);
        }

        panelPrincipal.add(panelBloques, BorderLayout.CENTER);
        add(panelPrincipal);

        actualizarVista();
    }

    public void actualizarVista() {
        for (int i = 0; i < 6; i++) {
            Cliente cliente = clientesMostrados[i];

            if (cliente != null) {
                etiquetasId[i].setText(cliente.getId());
                String estado = estadoClientes.getOrDefault(cliente.getId(), "");

                // Si no hay estado especial, y es preferencial, mostrar "[Preferencial]"
                if (estado.isEmpty() && cliente.isPreferencial()) {
                    estado = "[Preferencial]";
                    etiquetasEstado[i].setForeground(new Color(0, 128, 0)); // Verde oscuro
                } else if ("llamando...".equals(estado)) {
                    etiquetasEstado[i].setForeground(Color.RED);
                } else if ("en atención...".equals(estado)) {
                    etiquetasEstado[i].setForeground(Color.BLUE);
                } else {
                    etiquetasEstado[i].setForeground(Color.BLACK);
                }

                etiquetasEstado[i].setText(estado);

                // Color del ID (solo rojo o azul si en llamada o atención)
                if ("llamando...".equals(estado)) {
                    etiquetasId[i].setForeground(Color.RED);
                } else if ("en atención...".equals(estado)) {
                    etiquetasId[i].setForeground(Color.BLUE);
                } else {
                    etiquetasId[i].setForeground(Color.BLACK);
                }

            } else {
                etiquetasId[i].setText("");
                etiquetasEstado[i].setText("");
                etiquetasId[i].setForeground(Color.BLACK);
                etiquetasEstado[i].setForeground(Color.BLACK);
            }
        }

        // Sincronizar clientesMostrados[] si hay espacio vacío
        List<Cliente> cola = gestionCola.getCola();
        int indiceCola = 0;

        for (int i = 0; i < 6; i++) {
            if (clientesMostrados[i] == null) {
                while (indiceCola < cola.size()) {
                    Cliente candidato = cola.get(indiceCola++);
                    boolean yaMostrado = false;
                    for (Cliente c : clientesMostrados) {
                        if (c != null && c.getId().equals(candidato.getId())) {
                            yaMostrado = true;
                            break;
                        }
                    }
                    if (!yaMostrado) {
                        clientesMostrados[i] = candidato;
                        etiquetasId[i].setText(candidato.getId());

                        String estado = estadoClientes.getOrDefault(candidato.getId(), "");

                        if (estado.isEmpty() && candidato.isPreferencial()) {
                            estado = "[Preferencial]";
                            etiquetasEstado[i].setForeground(new Color(0, 128, 0)); // Verde oscuro
                        } else if ("llamando...".equals(estado)) {
                            etiquetasEstado[i].setForeground(Color.RED);
                        } else if ("en atención...".equals(estado)) {
                            etiquetasEstado[i].setForeground(Color.BLUE);
                        } else {
                            etiquetasEstado[i].setForeground(Color.BLACK);
                        }

                        etiquetasEstado[i].setText(estado);

                        // Color del ID
                        if ("llamando...".equals(estado)) {
                            etiquetasId[i].setForeground(Color.RED);
                        } else if ("en atención...".equals(estado)) {
                            etiquetasId[i].setForeground(Color.BLUE);
                        } else {
                            etiquetasId[i].setForeground(Color.BLACK);
                        }

                        break;
                    }
                }
            }
        }
    }



    
    public void marcarLlamando(Cliente cliente) {
        if (cliente != null) {
            estadoClientes.put(cliente.getId(), "llamando...");
            actualizarVista();
        }
    }

    public void marcarEnAtencion(Cliente cliente) {
        if (cliente != null) {
            estadoClientes.put(cliente.getId(), "en atención...");
            actualizarVista();
        }
    }

    public void eliminarCliente(Cliente cliente) {
        if (cliente == null) {
            return;
        }

        estadoClientes.remove(cliente.getId());

        // Buscar la posición del cliente
        int index = -1;
        for (int i = 0; i < clientesMostrados.length; i++) {
            if (clientesMostrados[i] != null && clientesMostrados[i].getId().equals(cliente.getId())) {
                index = i;
                break;
            }
        }

        // Si no se encuentra, no hacemos nada
        if (index == -1) {
            return;
        }

        // Desplazar todos los clientes a la izquierda a partir de esa posición
        for (int i = index; i < clientesMostrados.length - 1; i++) {
            clientesMostrados[i] = clientesMostrados[i + 1];
        }

        // Colocar null en la última posición
        clientesMostrados[clientesMostrados.length - 1] = null;

        // Luego tratamos de rellenar el último bloque con alguien nuevo de la cola (si hay)
        List<Cliente> cola = gestionCola.getCola();
        for (Cliente c : cola) {
            boolean yaMostrado = false;
            for (Cliente cm : clientesMostrados) {
                if (cm != null && cm.getId().equals(c.getId())) {
                    yaMostrado = true;
                    break;
                }
            }
            if (!yaMostrado) {
                clientesMostrados[clientesMostrados.length - 1] = c;
                break;
            }
        }

        // Actualizar la vista completa
        actualizarVista();
    }

}
