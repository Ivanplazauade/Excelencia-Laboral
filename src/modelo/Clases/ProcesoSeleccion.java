package modelo.Clases;

import modelo.Cliente;
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

    public ProcesoSeleccion(int id, String puesto, String descripcion, List<Skill> skillsRequeridas) {
        this.idProceso = id;
        this.puesto = puesto;
        this.descripcion = descripcion;
        this.fechaApertura = LocalDate.now();
        this.estado = EstadoVacante.ABIERTA;
        this.skillsRequeridas = (skillsRequeridas != null) ? skillsRequeridas : new ArrayList<>();
        this.postulaciones = new ArrayList<>();
        this.vacantesDisponibles = 1; // por defecto
    }

    // ======= Getters y Setters =======
    public int getId() { return idProceso; }
    public String getPuesto() { return puesto; }
    public String getDescripcion() { return descripcion; }
    public LocalDate getFechaApertura() { return fechaApertura; }
    public EstadoVacante getEstado() { return estado; }
    public List<Skill> getSkillsRequeridas() { return skillsRequeridas; }
    public List<Postulacion> getPostulaciones() { return postulaciones; }

    public String getUbicacion() { return ubicacion; }
    public double getSalarioMin() { return salarioMin; }
    public double getSalarioMax() { return salarioMax; }
    public int getVacantesDisponibles() { return vacantesDisponibles; }

    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public void setRangoSalarial(double min, double max) {
        this.salarioMin = min;
        this.salarioMax = max;
    }
    public void setVacantesDisponibles(int vacantes) { this.vacantesDisponibles = vacantes; }

    // ======= Lógica del proceso =======
    public void agregarPostulacion(Postulacion p) {
        postulaciones.add(p);
    }

    public void cerrarProceso() {
        this.estado = EstadoVacante.CERRADO;
    }

    public void decrementarVacantes() {
        if (vacantesDisponibles > 0) vacantesDisponibles--;
        if (vacantesDisponibles == 0) cerrarProceso();
    }
}
