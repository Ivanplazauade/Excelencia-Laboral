package modelo.Clases;

import modelo.Postulacion;
import java.util.List;

public class Empleado extends Persona{

    private int legajo;
    private String puesto;
    private double salario;
    private Usuario usuario;

    public Empleado(int id, String nombre, String apellido, String email, int legajo) {
        super(id, nombre, apellido, null, email, null, null);
        this.legajo = legajo;
        this.puesto = "Sin asignar";
        this.salario = 0.0;
    }

    public Empleado(int id, String nombre, String apellido, String dni, String email, String telefono, Direccion direccion,
                    int legajo, String puesto, double salario, Usuario usuario) {
        super(id, nombre, apellido, dni, email, telefono, direccion); // Llama al constructor de Persona
        this.legajo = legajo;
        this.puesto = puesto;
        this.salario = salario;
        this.usuario = usuario;
    }

    public ProcesoSeleccion crearProcesoSeleccion(int idProceso, Cliente c, String puesto, List<Skill> skillsRequeridas) {
        // En un sistema real, esto devolvería el objeto para que el gestor lo persista.
        ProcesoSeleccion nuevoProceso = new ProcesoSeleccion(idProceso, puesto, "Creado por Reclutador", skillsRequeridas);
        nuevoProceso.setCliente(c);
        nuevoProceso.setResponsable(this);
        return nuevoProceso;
    }

    public Evaluacion evaluarPostulante(int idEvaluacion, Postulacion postulacion, String observaciones, int puntaje) {
        Evaluacion evaluacion = new Evaluacion(idEvaluacion, observaciones, puntaje, this);
        // Aquí se llamaría al Gestor de Postulaciones para agregar la evaluación
        // postulacion.agregarEvaluacion(evaluacion);
        return evaluacion;
    }

    @Override
    public String getNombreCompleto() {
        return getNombre() + " " + getApellido() + " (" + puesto + ")";
    }

    @Override
    public String getTipoPersona() {
        return "Empleado";
    }

    public int getLegajo() { return legajo; }
    public void setLegajo(int legajo) { this.legajo = legajo; }

    public String getPuesto() { return puesto; }
    public void setPuesto(String puesto) { this.puesto = puesto; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public double getSalario() { return salario; }
    public void setSalario(double salario) { this.salario = salario; }
}