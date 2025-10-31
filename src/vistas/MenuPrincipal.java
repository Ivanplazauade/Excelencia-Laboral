package vistas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent; // <- Importación necesaria

public class MenuPrincipal extends JPanel {
    public MenuPrincipal(MainFrame frame) {
        // Usamos FlowLayout para que los botones se coloquen en una fila (más típico para un menú superior)
        setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));

        JButton btnCandidatos = new JButton("Gestionar Candidatos");
        JButton btnProcesos = new JButton("Gestionar Procesos");
        JButton btnPostulacion = new JButton("Crear Postulación"); // Se cambió el nombre
        JButton btnSalir = new JButton("Salir");

        // Los métodos ActionEvent se llaman con 'e'
        btnCandidatos.addActionListener(e -> frame.mostrarVistaPostulantes(e));
        btnProcesos.addActionListener(e -> frame.mostrarVistaProcesos(e));
        btnSalir.addActionListener(e -> System.exit(0));

        add(btnCandidatos);
        add(btnProcesos);
        add(btnPostulacion);
        add(btnSalir);
    }
}