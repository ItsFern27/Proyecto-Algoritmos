package newpackage;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        GestionCola gestionCola = new GestionCola();

        SwingUtilities.invokeLater(() -> {
            // Crea primero la ventana de la cola
            VentanaMostrarCola ventanaCola = new VentanaMostrarCola(gestionCola);
            ventanaCola.setVisible(true);
            
            
            VentanaClientesVisual ventanaClientesVisual = new VentanaClientesVisual(gestionCola);
            ventanaClientesVisual.setVisible(true);

            // Ahora crea la ventana de ingreso y pásale gestionCola
            VentanaIngresoCliente ventanaIngreso = new VentanaIngresoCliente(gestionCola, ventanaCola, ventanaClientesVisual);
            ventanaIngreso.setVisible(true);

            // Crea la ventana de control de cajas y pásale gestionCola y ventanaCola
            VentanaControlCajas controlCajas = new VentanaControlCajas(gestionCola, ventanaCola, ventanaClientesVisual);
            controlCajas.setVisible(true);
            
            
        });
    }
}