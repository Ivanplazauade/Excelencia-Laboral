package vistas;


import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JPanel {
    public MenuPrincipal(MainFrame frame) {
        setLayout(new FlowLayout(FlowLayout.LEFT, 15, 12));

        JButton btnCandidatos = new JButton("Gestionar Candidatos");
        JButton btnProcesos = new JButton("Gestionar Procesos");
        JButton btnSalir = new JButton("Salir");

        btnCandidatos.addActionListener(frame::mostrarVistaPostulantes);
        btnProcesos.addActionListener(frame::mostrarVistaProcesos);
        btnSalir.addActionListener(e -> System.exit(0));

        add(btnCandidatos);
        add(btnProcesos);
        add(btnSalir);
    }
}
