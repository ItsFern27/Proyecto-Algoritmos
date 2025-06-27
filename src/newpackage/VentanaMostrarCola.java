package newpackage;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaMostrarCola extends JFrame {
    private JTable tablaCola;
    private DefaultTableModel modeloTabla;

    public VentanaMostrarCola(List<Cliente> cola, List<Caja> cajas) {
        setTitle("Ventana de turno de clientes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);

        String[] columnas = { "DNI", "ID", "Estado" };
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaCola = new JTable(modeloTabla);
        tablaCola.setRowHeight(28);

        // Renderizado de color para preferenciales y estados
        tablaCola.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus,
                    int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String estado = (String) table.getValueAt(row, 2);
                if (estado.contains("Atendiendo")) {
                    c.setForeground(new Color(180, 0, 0));
                    c.setFont(c.getFont().deriveFont(Font.BOLD));
                } else if (estado.contains("Llamando")) {
                    c.setForeground(new Color(180, 80, 80));
                    c.setFont(c.getFont().deriveFont(Font.BOLD));
                } else if (estado.contains("Preferencial")) {
                    c.setForeground(new Color(0, 140, 0));
                } else {
                    c.setForeground(Color.BLACK);
                }
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(tablaCola);
        add(scrollPane, BorderLayout.CENTER);

        actualizarCola(cola, cajas);
    }

    public void actualizarCola(List<Cliente> cola, List<Caja> cajas) {
        modeloTabla.setRowCount(0);

        // Identificar cajas disponibles y habilitadas
        java.util.List<Caja> cajasDisponibles = new java.util.ArrayList<>();
        for (Caja caja : cajas) {
            if (caja.estaHabilitada() && caja.estaDisponible()) {
                cajasDisponibles.add(caja);
            }
        }

        int idxLlamando = 0; // Índice para recorrer las cajas disponibles

        for (int i = 0; i < cola.size(); i++) {
            Cliente cliente = cola.get(i);
            String estado = "";

            // Buscar si el cliente está siendo atendido por alguna caja
            for (Caja caja : cajas) {
                Cliente actual = caja.getClienteActual();
                if (actual != null && actual.getId().equals(cliente.getId())) {
                    if (!caja.estaDisponible()) {
                        estado = "Atendiendo (" + caja.getNombre() + ")";
                        break;
                    }
                }
            }

            // Si no está siendo atendido, ver si le toca ser llamado por una caja
            // disponible
            if (estado.isEmpty() && idxLlamando < cajasDisponibles.size()) {
                estado = "Llamando (" + cajasDisponibles.get(idxLlamando).getNombre() + ")";
                idxLlamando++;
            }

            // Preferencial
            if (estado.isEmpty() && cliente.isPreferencial()) {
                estado = "Preferencial";
            }

            // Normal
            if (estado.isEmpty()) {
                estado = "En espera";
            }

            modeloTabla.addRow(new Object[] {
                    String.valueOf(cliente.getDni()),
                    cliente.getId(),
                    estado
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Ejemplo de datos de prueba
            List<Cliente> cola = java.util.Arrays.asList(
                    new Cliente(),
                    new Cliente(),
                    new Cliente(),
                    new Cliente(),
                    new Cliente(),
                    new Cliente(),
                    new Cliente(),
                    new Cliente(),
                    new Cliente());
            List<Caja> cajas = java.util.Arrays.asList(
                    new Caja("Caja 1"),
                    new Caja("Caja 2"));
            VentanaMostrarCola ventana = new VentanaMostrarCola(cola, cajas);
            ventana.setVisible(true);
        });
    }
}
