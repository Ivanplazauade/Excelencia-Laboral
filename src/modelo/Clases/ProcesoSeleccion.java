package modelo.Clases;

import modelo.Postulacion;
import modelo.enums.EstadoVacante;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProcesoSeleccion {
    private int idProceso;
    private String puesto;
    private String descripcion;
    private LocalDate fechaApertura;
    private EstadoVacante estado;
    private Cliente cliente;
    private Empleado responsable;
    private List<Skill> skillsRequeridas;
    private List<Postulacion> postulaciones;
    private String ubicacion;
    private double salarioMin;
    private double salarioMax;
    private int vacantesDisponibles;

    public ProcesoSeleccion() {
    }

    public ProcesoSeleccion(int id, String puesto, String descripcion, List<Skill> skillsRequeridas) {
        this.idProceso = id;
        this.puesto = puesto;
        this.descripcion = descripcion;
        this.fechaApertura = LocalDate.now();
        this.estado = EstadoVacante.ABIERTA;
        this.skillsRequeridas = (skillsRequeridas != null) ? skillsRequeridas : new ArrayList<>();
        this.postulaciones = new ArrayList<>();
        this.vacantesDisponibles = 1;
    }

    // ======= Getters y Setters =======
    public int getId() { return idProceso; }
    public String getPuesto() { return puesto; }
    public String getDescripcion() { return descripcion; }
    public LocalDate getFechaApertura() { return fechaApertura; }
    public EstadoVacante getEstado() { return estado; }
    public Cliente getCliente() { return cliente; }
    public Empleado getResponsable() { return responsable; }
    public List<Skill> getSkillsRequeridas() { return skillsRequeridas; }
    public List<Postulacion> getPostulaciones() { return postulaciones; }
    public String getUbicacion() { return ubicacion; }
    public double getSalarioMin() { return salarioMin; }
    public double getSalarioMax() { return salarioMax; }
    public int getVacantesDisponibles() { return vacantesDisponibles; }

    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public void setResponsable(Empleado responsable) { this.responsable = responsable; }
    public void setEstado(EstadoVacante estado) { this.estado = estado; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public void setId(int id) {
        this.idProceso = id;
    }
    public void setFechaApertura(LocalDate fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public void setRangoSalarial(double min, double max) {
        this.salarioMin = min;
        this.salarioMax = max;
    }
    public void setVacantesDisponibles(int vacantes) { this.vacantesDisponibles = vacantes; }

    // ======= Lógica del proceso =======
    public void agregarPostulacion(Postulacion p) {
        if (p != null) postulaciones.add(p);
    }

    public void cerrarProceso() {
        this.estado = EstadoVacante.CERRADA;
    }

    public void decrementarVacantes() {
        if (vacantesDisponibles > 0) vacantesDisponibles--;
        if (vacantesDisponibles == 0) cerrarProceso();
    }

    @Override
    public String toString() {
        return "Proceso #" + idProceso + " - Puesto: " + puesto + " (" + estado + ")";
    }
}
