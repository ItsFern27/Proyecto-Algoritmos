package newpackage;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        GestionCola gestionCola = new GestionCola();

        // Mostrar primero la ventana de ingreso de cliente
        SwingUtilities.invokeLater(() -> {
            VentanaIngresoCliente ventanaIngreso = new VentanaIngresoCliente(gestionCola);
            ventanaIngreso.setVisible(true);
            VentanaControlCajas controlCajas = new VentanaControlCajas(gestionCola);
            controlCajas.setVisible(true);

            // Cuando se cierre la ventana de ingreso, mostrar la ventana de la cola
            ventanaIngreso.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    ventanaIngreso.dispose();
                    VentanaMostrarCola ventanaCola = new VentanaMostrarCola(gestionCola);
                    ventanaCola.setVisible(true);
                }
            });
        });
    }
}
