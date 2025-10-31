package vistas;

import modelo.Clases.ProcesoSeleccion;
import servicios.ProcesoSeleccionService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VistaProcesoSeleccion extends JInternalFrame {

    private JList<ProcesoSeleccion> listaProcesos;
    private ProcesoSeleccionService service;

    public VistaProcesoSeleccion() {
        super("Gestión de Procesos de Selección", true, true, true, true);
        setSize(600, 450);
        setLayout(new BorderLayout());

        this.service = ProcesoSeleccionService.getInstancia();

        // Panel Norte: Filtros y Botones
        JPanel panelFiltros = new JPanel();
        panelFiltros.add(new JButton("Crear Nuevo Proceso"));
        panelFiltros.add(new JButton("Cerrar Proceso Seleccionado"));
        add(panelFiltros, BorderLayout.NORTH);

        // Panel Central: Lista de Procesos
        listaProcesos = new JList<>();
        listaProcesos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(listaProcesos);
        add(scrollPane, BorderLayout.CENTER);

        cargarListaProcesos();
    }

    private void cargarListaProcesos() {
        try {
            List<ProcesoSeleccion> procesos = service.listarTodos();
            ProcesoSeleccion[] array = procesos.toArray(new ProcesoSeleccion[0]);
            listaProcesos.setListData(array);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar los procesos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Aquí irían los métodos para crear/modificar/cerrar procesos, interactuando con ProcesoSeleccionService.
}