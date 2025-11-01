package vistas;

import modelo.Clases.Postulante;
import modelo.Clases.Direccion;
import servicios.PostulanteService;
import excepciones.EdadInvalidaException;
import excepciones.EmailInvalidoException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent; // <--- Importación de ActionEvent añadida
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class VistaPostulante extends JInternalFrame {
    private JPanel panelCandidato;
    private JTextField txtId, txtNombre, txtApellido, txtDni, txtEmail, txtFechaNac;
    private JTextField txtDireccion, txtTelefono, txtCiudad, txtProvincia, txtCalle, txtNumero, txtCV, txtCodigoPostal;
    private JButton btnGuardar, btnNuevo, btnBuscar, btnVolver;
    private JList<Postulante> listaPostulantes;
    private PostulanteService service;
    private JTextArea txtResultado;

    public VistaPostulante() {
        super("Gestión de Postulantes", true, true, true, true);
        setSize(750, 500);
        setLayout(new BorderLayout());

        this.service = PostulanteService.getInstancia();

        JPanel panelFormulario = crearPanelFormulario();
        add(panelFormulario, BorderLayout.NORTH);

        listaPostulantes = new JList<>();
        listaPostulantes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaPostulantes.addListSelectionListener(e -> cargarPostulanteSeleccionado());
        JScrollPane scrollPane = new JScrollPane(listaPostulantes);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotones = crearPanelBotones();
        add(panelBotones, BorderLayout.SOUTH);

        cargarListaPostulantes();
    }

    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));

        txtId = new JTextField(5);
        txtId.setEditable(false);
        txtNombre = new JTextField(20);
        txtApellido = new JTextField(20);
        txtDni = new JTextField(20);
        txtEmail = new JTextField(20);
        txtFechaNac = new JTextField("AAAA-MM-DD");

        panel.add(new JLabel("ID:"));
        panel.add(txtId);
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Apellido:"));
        panel.add(txtApellido);
        panel.add(new JLabel("DNI:"));
        panel.add(txtDni);
        panel.add(new JLabel("Email:"));
        panel.add(txtEmail);
        panel.add(new JLabel("Fecha Nac. (AAAA-MM-DD):"));
        panel.add(txtFechaNac);

        return panel;
    }

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel();

        btnNuevo = new JButton("Nuevo");
        btnGuardar = new JButton("Guardar");
        btnBuscar = new JButton("Buscar");

        btnNuevo.addActionListener(e -> limpiarCampos());
        btnGuardar.addActionListener(this::guardarPostulante);

        panel.add(btnNuevo);
        panel.add(btnGuardar);
        panel.add(btnBuscar);

        return panel;
    }

    private void cargarListaPostulantes() {
        try {
            List<Postulante> postulantes = service.listarTodos();
            Postulante[] array = postulantes.toArray(new Postulante[0]);
            listaPostulantes.setListData(array);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar la lista: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtDni.setText("");
        txtEmail.setText("");
        txtFechaNac.setText("AAAA-MM-DD");
        listaPostulantes.clearSelection();
    }

    private void cargarPostulanteSeleccionado() {
        Postulante p = listaPostulantes.getSelectedValue();
        if (p != null) {
            txtId.setText(String.valueOf(p.getIdPersona()));
            txtNombre.setText(p.getNombre());
            txtApellido.setText(p.getApellido());
            txtDni.setText(p.getDni());
            txtEmail.setText(p.getEmail());
            txtFechaNac.setText(p.getFechaNacimiento().toString());
        }
    }

    private void guardarPostulante(ActionEvent e) {
        try {
            int id = txtId.getText().isEmpty() ? 0 : Integer.parseInt(txtId.getText());

            Direccion d = new Direccion("Calle N/A", "Ciudad N/A", "Provincia N/A", "0", "0");

            Postulante p = new Postulante(
                    id,
                    txtNombre.getText(),
                    txtApellido.getText(),
                    txtDni.getText(),
                    txtEmail.getText(),
                    null,
                    LocalDate.parse(txtFechaNac.getText()),
                    d,
                    "CV_TEMP"
            );

            service.guardar(p);
            JOptionPane.showMessageDialog(this, "Postulante guardado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            cargarListaPostulantes();

        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Error: El formato de fecha debe ser AAAA-MM-DD.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (EdadInvalidaException | EmailInvalidoException ex) {
            JOptionPane.showMessageDialog(this, "Error de Validación: " + ex.getMessage(), "Error de Negocio", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage(), "Error General", JOptionPane.ERROR_MESSAGE);
        }
    }
}