package newpackage;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

public class VentanaMostrarCola extends JFrame {
    private GestionCola gestionCola;
    private JPanel panelLista;

    public VentanaMostrarCola(GestionCola gestionCola) {
        this.gestionCola = gestionCola;
        setTitle("Ventana de turno de clientes");
        setSize(650, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Título de la ventana
        JLabel titulo = new JLabel("Cola Total Actual");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Panel donde se mostrarán los clientes (dos columnas)
        panelLista = new JPanel();
        panelLista.setLayout(new GridLayout(0, 2, 10, 2));
        panelLista.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                "",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.BOLD, 12)));

        // Scroll para el panel de la lista
        JScrollPane scroll = new JScrollPane(panelLista);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        setLayout(new BorderLayout());
        add(titulo, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        // Llenar la lista al iniciar
        actualizarCola();
    }

    // Método para actualizar la visualización de la cola de clientes
    public void actualizarCola() {
        panelLista.removeAll(); // Limpiar panel antes de volver a llenarlo
        List<Cliente> cola = gestionCola.getCola();

        for (Cliente c : cola) {
            // Columna izquierda: DNI del cliente
            JLabel lblDni = new JLabel("Cliente: " + String.format("%08d", c.getDni()));
            lblDni.setFont(new Font("Arial", Font.PLAIN, 15));
            panelLista.add(lblDni);

            // Columna derecha: Estado del cliente (ID, estado, caja, preferencial)
            JLabel lblEstado = new JLabel();
            String texto = c.getId();
            Color color = Color.RED;
            Font font = new Font("Arial", Font.BOLD, 15);

            {
                // Si es preferencial, mostrarlo en verde
                if (c.isPreferencial()) {
                    texto = texto + " [Preferencial]";
                    color = new Color(0, 150, 0);
                }
                // Aquí puedes agregar lógica para mostrar "Atendiendo", "Llamando", etc. si
                // tienes esa info
            }

            // Si Cliente tiene métodos getEstado() y getCajaAsignada(), mostrar estado y
            // caja
            try {
                java.lang.reflect.Method getEstado = c.getClass().getMethod("getEstado");
                java.lang.reflect.Method getCaja = c.getClass().getMethod("getCajaAsignada");
                String estado = (String) getEstado.invoke(c);
                String caja = (String) getCaja.invoke(c);

                if ("Atendiendo".equalsIgnoreCase(estado) || estado.startsWith("Atendido")) {
                    texto = c.getId() + " (Atendiendo) -> " + caja;
                    color = new Color(192, 57, 43); // Rojo fuerte
                    font = new Font("Arial", Font.BOLD, 15);
                } else if ("Llamando".equalsIgnoreCase(estado) || estado.startsWith("Llamado")) {
                    texto = c.getId() + " (Llamando) -> " + caja;
                    color = new Color(185, 119, 14); // Naranja
                    font = new Font("Arial", Font.BOLD, 15);
                } else if (c.isPreferencial()) {
                    texto = c.getId() + " [Preferencial]";
                    color = new Color(0, 150, 0); // Verde
                    font = new Font("Arial", Font.BOLD, 15);
                } else {
                    texto = c.getId();
                    color = new Color(176, 58, 46); // Rojo oscuro
                    font = new Font("Arial", Font.BOLD, 15);
                }
            } catch (Exception ex) {
                // Si no existen los métodos, solo mostrar preferencial
            }

            lblEstado.setText(texto);
            lblEstado.setFont(font);
            lblEstado.setForeground(color);
            panelLista.add(lblEstado);
        }

        // Refrescar el panel
        panelLista.revalidate();
        panelLista.repaint();
    }

}
