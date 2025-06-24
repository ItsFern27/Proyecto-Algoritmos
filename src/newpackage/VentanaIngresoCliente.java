package newpackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaIngresoCliente extends JFrame {
    private JTextField campoDni;
    private JLabel lblTicket;
    private JLabel lblTipo;
    private GestionCola gestionCola;

    public VentanaIngresoCliente(GestionCola gestionCola) {
        this.gestionCola = gestionCola;

        setTitle("Ventana de obtención de ticket");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel lblTitulo = new JLabel("INGRESE SU DNI PARA OBTENER SU TICKET:");
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalStrut(20));
        panel.add(lblTitulo);

        // Campo de texto + botón
        JPanel panelInput = new JPanel();
        campoDni = new JTextField(10);
        JButton btnEnviar = new JButton("▶");
        btnEnviar.setFocusable(false);

        panelInput.add(campoDni);
        panelInput.add(btnEnviar);
        panel.add(panelInput);

        // Labels de respuesta
        lblTicket = new JLabel("Número de ticket: (En espera...)");
        lblTipo = new JLabel("Tipo de cliente: (Normal / Preferencial)");
        lblTicket.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTipo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Ajustes de margen superior
        lblTicket.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        lblTipo.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));

        // Espacio antes de los resultados (reducido)
        panel.add(Box.createVerticalStrut(5));
        panel.add(lblTicket);
        panel.add(lblTipo);

        add(panel, BorderLayout.CENTER);

        // Evento al hacer clic
        btnEnviar.addActionListener(e -> procesarIngreso());
    }

    private void procesarIngreso() {
        String dniTexto = campoDni.getText().trim();

        // Validar que tenga exactamente 8 dígitos y solo números
        if (!dniTexto.matches("\\d{8}")) {
            JOptionPane.showMessageDialog(this, "El DNI debe tener exactamente 8 dígitos numéricos.", "DNI inválido", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Si pasó la validación, convertimos y seguimos
        int dni = Integer.parseInt(dniTexto);
        int edad = 18 + (int)(Math.random() * 70); // Edad aleatoria

        Cliente nuevoCliente = new Cliente(edad);
        nuevoCliente.setDni(dni);

        gestionCola.insertarCliente(nuevoCliente);

        // ⬇️ AÑADIDO: imprimir el contenido de la cola
    System.out.println("Cola actual:");
    gestionCola.mostrarCola();

        // Mostrar resultados
        lblTicket.setText("Número de ticket: " + nuevoCliente.getId());
        lblTipo.setText("Tipo de cliente: " + (nuevoCliente.isPreferencial() ? "Preferencial" : "Normal"));

        campoDni.setText("");
    }

    public static void main(String[] args) {
        GestionCola gestionCola = new GestionCola();
        SwingUtilities.invokeLater(() -> new VentanaIngresoCliente(gestionCola).setVisible(true));
    }
}
