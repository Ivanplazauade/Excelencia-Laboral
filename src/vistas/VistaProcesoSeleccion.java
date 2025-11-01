package vistas;

import modelo.Clases.*;
import modelo.enums.EstadoVacante;
import servicios.ProcesoSeleccionService;
import servicios.PostulacionService;

import excepciones.CandidatoInactivoException;
import excepciones.VacanteCerradaException;
import excepciones.PostulacionDuplicadaException;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class VistaProcesoSeleccion extends JInternalFrame {

    // ===== Campos (.form o creados por código) =====
    private JPanel panelProceso;
    private JList<ProcesoSeleccion> listaProcesos;
    private JButton btnNuevoProceso;
    private JButton btnCerrarProceso;
    private JButton btnCrearPostulacion;
    private JButton btnRefrescar;

    // ===== Servicios =====
    private final ProcesoSeleccionService procesoService = ProcesoSeleccionService.getInstancia();
    private final PostulacionService postulacionService = PostulacionService.getInstancia();

    // ===== Modelo de lista =====
    private final DefaultListModel<ProcesoSeleccion> listModel = new DefaultListModel<>();

    public VistaProcesoSeleccion() {
        super("Gestión de Procesos de Selección", true, true, true, true);

        if (panelProceso != null) {
            setContentPane(panelProceso);
            armarComponentesSiFaltan();
        } else {
            setContentPane(crearUIManual());
        }

        listaProcesos.setModel(listModel);
        listaProcesos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        inicializarEventos();

        cargarListaProcesos();

        setSize(800, 600);
        setVisible(true);
    }

    private JPanel crearUIManual() {
        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Norte: barra de acciones
        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        btnNuevoProceso = new JButton("Nuevo Proceso");
        btnCerrarProceso = new JButton("Cerrar Proceso");
        btnCrearPostulacion = new JButton("Crear Postulación…");
        btnRefrescar = new JButton("Refrescar");
        acciones.add(btnNuevoProceso);
        acciones.add(btnCerrarProceso);
        acciones.add(btnCrearPostulacion);
        acciones.add(btnRefrescar);
        root.add(acciones, BorderLayout.NORTH);

        // Centro: lista dentro de scroll
        listaProcesos = new JList<>();
        root.add(new JScrollPane(listaProcesos), BorderLayout.CENTER);

        return root;
    }

    private void armarComponentesSiFaltan() {
        // Busca un contenedor en el NORTH para colocar botones si no existen
        Container cp = panelProceso;
        if (btnNuevoProceso == null) {
            btnNuevoProceso = new JButton("Nuevo Proceso");
        }
        if (btnCerrarProceso == null) {
            btnCerrarProceso = new JButton("Cerrar Proceso");
        }
        if (btnCrearPostulacion == null) {
            btnCrearPostulacion = new JButton("Crear Postulación…");
        }
        if (btnRefrescar == null) {
            btnRefrescar = new JButton("Refrescar");
        }
        if (listaProcesos == null) {
            listaProcesos = new JList<>();
            panelProceso.setLayout(new BorderLayout(12, 12));
            JPanel barra = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
            barra.add(btnNuevoProceso);
            barra.add(btnCerrarProceso);
            barra.add(btnCrearPostulacion);
            barra.add(btnRefrescar);
            panelProceso.add(barra, BorderLayout.NORTH);
            panelProceso.add(new JScrollPane(listaProcesos), BorderLayout.CENTER);
        }
    }

    private void inicializarEventos() {
        btnRefrescar.addActionListener(e -> cargarListaProcesos());
        btnCerrarProceso.addActionListener(e -> cerrarProcesoSeleccionado());
        btnCrearPostulacion.addActionListener(e -> crearPostulacionParaProceso());
        btnNuevoProceso.addActionListener(e -> crearProcesoSimple());
    }


    private void cargarListaProcesos() {
        listModel.clear();
        try {
            List<ProcesoSeleccion> procesos = procesoService.listarTodos();
            for (ProcesoSeleccion p : procesos) listModel.addElement(p);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar procesos: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private ProcesoSeleccion getProcesoSeleccionado() {
        ProcesoSeleccion sel = listaProcesos.getSelectedValue();
        if (sel == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un proceso de la lista.");
        }
        return sel;
    }

    private void cerrarProcesoSeleccionado() {
        ProcesoSeleccion sel = getProcesoSeleccionado();
        if (sel == null) return;

        int ok = JOptionPane.showConfirmDialog(this,
                "¿Cerrar el proceso \"" + sel.getCliente().getNombre() + "\"?",
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (ok != JOptionPane.YES_OPTION) return;

        try {
            sel.setEstado(EstadoVacante.CERRADA);
            procesoService.cerrarProceso(sel.getId());
            cargarListaProcesos();
            JOptionPane.showMessageDialog(this, "Proceso cerrado.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "No se pudo cerrar el proceso: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void crearPostulacionParaProceso() {
        ProcesoSeleccion sel = getProcesoSeleccionado();
        if (sel == null) return;

        String input = JOptionPane.showInputDialog(this,
                "ID del postulante a postular en el proceso \"" + sel.getId() + "\":",
                "Crear Postulación", JOptionPane.QUESTION_MESSAGE);

        if (input == null || input.isBlank()) return;

        try {
            int postulanteId = Integer.parseInt(input.trim());
            int procesoId = sel.getId();

            postulacionService.crearPostulacion(postulanteId, procesoId);

            JOptionPane.showMessageDialog(this, "Postulación creada correctamente.");
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "El ID de postulante debe ser numérico.");
        } catch (CandidatoInactivoException | VacanteCerradaException | PostulacionDuplicadaException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validación", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al crear la postulación: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void crearProcesoSimple() {
        String puesto = JOptionPane.showInputDialog(
                this, "Ingrese el puesto del nuevo proceso de selección:",
                "Nuevo Proceso", JOptionPane.QUESTION_MESSAGE);

        if (puesto == null || puesto.isBlank()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un nombre de puesto válido.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Cliente cliente = null;
            Empleado responsable = null;
            List<Skill> requeridas = new ArrayList<>();

            ProcesoSeleccion nuevo = procesoService.crearProceso(puesto.trim(), cliente, responsable, requeridas);

            // Refresca la lista visible en la interfaz
            cargarListaProcesos();

            JOptionPane.showMessageDialog(this,
                    "Proceso creado exitosamente:\n" + nuevo.getPuesto(),
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al crear el proceso: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }}


