package modelo;

import modelo.Clases.Direccion;
import modelo.Clases.Persona;
import modelo.Clases.ProcesoSeleccion;
import modelo.Clases.Skill;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cliente extends Persona {

    private String razonSocial;
    private String cuit;
    private List<ProcesoSeleccion> procesosActivos;

    // ====== Constructor ======
    public Cliente(int id, String nombre, String apellido, String email,
                   Direccion direccion, String razonSocial, String cuit) {
        super(id, nombre, apellido, email, direccion);
        this.razonSocial = razonSocial;
        this.cuit = cuit;
        this.procesosActivos = new ArrayList<>();
    }

    // ====== API de negocio ======

    /**
     * Alta “ligera” de un proceso solicitado por el cliente.
     * Suele coordinarse con GestorProcesos para asignar id, responsable, etc.
     */
    public ProcesoSeleccion solicitarProceso(int idProceso, String puesto, List<Skill> requeridas) {
        ProcesoSeleccion p = new ProcesoSeleccion(idProceso, puesto, "Pendiente de detalle", requeridas);
        // Si tu ProcesoSeleccion tiene setCliente(this), podés vincularlo:
        // p.setCliente(this);
        this.procesosActivos.add(p);
        return p;
    }

    /** Devuelve la lista inmutable de procesos activos del cliente. */
    public List<ProcesoSeleccion> getProcesosActivos() {
        return Collections.unmodifiableList(procesosActivos);
    }

    /** Alias semántico si querés mantener tu firma original. */
    public List<ProcesoSeleccion> verProcesosActivos() {
        return getProcesosActivos();
    }

    /** Permite agregar procesos creados por GestorProcesos. */
    public void agregarProceso(ProcesoSeleccion proceso) {
        if (proceso != null) {
            this.procesosActivos.add(proceso);
            // Si existe setCliente en ProcesoSeleccion, podés enlazar:
            // proceso.setCliente(this);
        }
    }

    // ====== Overrides de Persona ======

    @Override
    public String getNombreCompleto() {
        // Usá lo que prefieras mostrar: nombre + apellido o la razón social
        // Si la razón social existe, suele ser más útil para reportes:
        return (razonSocial != null && !razonSocial.isEmpty())
                ? razonSocial
                : (nombre + " " + apellido);
    }

    @Override
    public String getTipoPersona() {
        return "Cliente";
    }

    // ====== Getters opcionales ======
    public String getRazonSocial() { return razonSocial; }
    public String getCuit() { return cuit; }
}

