package modelo.Clases;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cliente extends Persona {

    private String razonSocial;
    private String cuit;
    private List<ProcesoSeleccion> procesosActivos;

    public Cliente(int id, String nombre, String apellido, String email, Direccion direccion, String razonSocial, String cuit) {
        super(id, nombre, apellido, null, email, null, direccion);
        this.razonSocial = razonSocial;
        this.cuit = cuit;
        this.procesosActivos = new ArrayList<>();
    }

    public Cliente(int idPersona, String nombre, String apellido, String dni, String email, String telefono,
                   Direccion direccion, String razonSocial, String cuit, List<ProcesoSeleccion> procesosActivos) {
        super(idPersona, nombre, apellido, dni, email, telefono, direccion);
        this.razonSocial = razonSocial;
        this.cuit = cuit;
        this.procesosActivos = (procesosActivos != null) ? procesosActivos : new ArrayList<>();
    }

    public ProcesoSeleccion solicitarProceso(int idProceso, String puesto, List<Skill> requeridas) {
        ProcesoSeleccion p = new ProcesoSeleccion(idProceso, puesto, "Solicitud recibida", requeridas);
        p.setCliente(this);
        this.procesosActivos.add(p);
        return p;
    }

    public List<ProcesoSeleccion> getProcesosActivos() {
        return Collections.unmodifiableList(procesosActivos);
    }

    public List<ProcesoSeleccion> verProcesosActivos() {
        return getProcesosActivos();
    }

    public void agregarProceso(ProcesoSeleccion proceso) {
        if (proceso != null && !this.procesosActivos.contains(proceso)) {
            proceso.setCliente(this);
            this.procesosActivos.add(proceso);
        }
    }

    @Override
    public String getNombreCompleto() {
        return (razonSocial != null && !razonSocial.isEmpty())
                ? razonSocial
                : (getNombre() + " " + getApellido());
    }

    @Override
    public String getTipoPersona() {
        return "Cliente";
    }

    public String getRazonSocial() { return razonSocial; }
    public String getCuit() { return cuit; }

    @Override
    public String toString() {
        return "Cliente: " + getNombreCompleto() + " (CUIT: " + cuit + ")";
    }
}