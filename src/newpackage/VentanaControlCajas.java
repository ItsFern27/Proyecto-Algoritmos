package newpackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class VentanaControlCajas extends JFrame {
    private GestionCola gestionCola;
    private List<Caja> cajas;
    private JLabel[] estadoCajas;
    private VentanaMostrarCola ventanaCola;// Para mostrarcola
    private VentanaClientesVisual ventanaClientesVisual;


    public VentanaControlCajas(GestionCola gestionCola, VentanaMostrarCola ventanaCola, VentanaClientesVisual ventanaClientesVisual) {
        this.gestionCola = gestionCola;
        this.cajas = new ArrayList<>();
        this.ventanaCola = ventanaCola;// Para mostrarcola
        this.ventanaClientesVisual = ventanaClientesVisual;
        
        cajas.add(new Caja("Caja 1"));
        cajas.add(new Caja("Caja 2"));

        setTitle("Control de Cajas");
        setSize(800, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(1, 2));

        estadoCajas = new JLabel[2];

        for (int i = 0; i < cajas.size(); i++) {
            JPanel panelCaja = crearPanelCaja(cajas.get(i), i);
            add(panelCaja);
        }
    }

    private JPanel crearPanelCaja(Caja caja, int index) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder(caja.getNombre()));

        estadoCajas[index] = new JLabel(caja.toString());
        estadoCajas[index].setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnHabilitar = new JButton("Habilitar");
        JButton btnDeshabilitar = new JButton("Deshabilitar");
        JButton btnLlamar = new JButton("Llamar Cliente");
        JButton btnFinalizar = new JButton("Finalizar Atención");

        btnHabilitar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDeshabilitar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLlamar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnFinalizar.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnHabilitar.addActionListener(e -> {
            caja.habilitar();
            actualizarEstado(index);
        });

        btnDeshabilitar.addActionListener(e -> {
            caja.deshabilitar();
            actualizarEstado(index);
        });

        btnLlamar.addActionListener(e -> {
            if (!caja.estaDisponible()) {
                JOptionPane.showMessageDialog(this, "Caja ocupada.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Cliente siguiente = gestionCola.verSiguiente();
            if (siguiente != null) {
                boolean respondio = caja.llamarCliente(siguiente);

                if (respondio) {
                    // Cliente respondió → lo quitamos de la cola y marcamos en atención
                    gestionCola.quitarCliente();
                    ventanaClientesVisual.marcarEnAtencion(siguiente);
                } else {
                    // Cliente no respondió → lo quitamos de la cola y de la vista
                    gestionCola.quitarCliente();
                    ventanaClientesVisual.eliminarCliente(siguiente);
                }
                
                
                ventanaCola.actualizarCola();
                
                ventanaClientesVisual.marcarLlamando(siguiente);  // Muestra "llamando..."

                if (caja.getClienteActual() != null) {
                    // Cliente respondió y está en atención
                    ventanaClientesVisual.marcarEnAtencion(siguiente);
                } else {
                    // Cliente no respondió → quitarlo de la visual
                    ventanaClientesVisual.eliminarCliente(siguiente);
                }

            } else {
                JOptionPane.showMessageDialog(this, "No hay clientes en cola.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }
            actualizarEstado(index);
        });

        btnFinalizar.addActionListener(e -> {
            Cliente atendido = caja.getClienteActual(); // CAPTURAMOS ANTES DE finalizarAtencion()
            caja.finalizarAtencion();
            if (atendido != null) {
                ventanaCola.actualizarCola(); // si lo estás usando
                ventanaClientesVisual.eliminarCliente(atendido); // 🔥 borra visualmente
            }
            actualizarEstado(index);
        });

        panel.add(Box.createVerticalStrut(10));
        panel.add(estadoCajas[index]);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnHabilitar);
        panel.add(btnDeshabilitar);
        panel.add(btnLlamar);
        panel.add(btnFinalizar);

        return panel;
    }

    private void actualizarEstado(int index) {
        estadoCajas[index].setText(cajas.get(index).toString());
    }

    /*public static void main(String[] args) {
        GestionCola gestionCola = new GestionCola();
        SwingUtilities.invokeLater(() -> {
            VentanaControlCajas ventana = new VentanaControlCajas(gestionCola);
            ventana.setVisible(true);
        });
    }
    */
}