package vistas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;

public class MainFrame extends JFrame {

    private JDesktopPane desktopPane;

    public MainFrame() {
        setTitle("Sistema de Gestión de Talento - Excelencia Laboral");
        setSize(1000, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        desktopPane = new JDesktopPane();
        add(desktopPane, BorderLayout.CENTER);

        // Cambiado de crearMenu() a añadir el panel de botones inicial
        add(new MenuPrincipal(this), BorderLayout.NORTH);
    }

    // Métodos públicos para ser llamados por MenuPrincipal
    public void mostrarVistaPostulantes(ActionEvent e) {
        VistaPostulante vista = new VistaPostulante();
        desktopPane.add(vista);
        vista.setVisible(true);
        try {
            vista.setSelected(true);
        } catch (java.beans.PropertyVetoException ex) { }
    }

    public void mostrarVistaProcesos(ActionEvent e) {
        VistaProcesoSeleccion vista = new VistaProcesoSeleccion();
        desktopPane.add(vista);
        vista.setVisible(true);
        try {
            vista.setSelected(true);
        } catch (java.beans.PropertyVetoException ex) { }
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}