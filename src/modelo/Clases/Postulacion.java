package modelo;

import modelo.Clases.Evaluacion;
import modelo.Clases.Postulante;
import modelo.Clases.ProcesoSeleccion;
import modelo.enums.EstadoPostulacion;
import java.time.LocalDate;

public class Postulacion {

    private int idPostulacion;
    private LocalDate fechaPostulacion;
    private EstadoPostulacion estado;
    private Postulante postulante;
    private ProcesoSeleccion proceso;
    private Evaluacion evaluacion;

    // ====== Constructores ======
    public Postulacion(int idPostulacion, Postulante postulante, ProcesoSeleccion proceso) {
        this.idPostulacion = idPostulacion;
        this.postulante = postulante;
        this.proceso = proceso;
        this.fechaPostulacion = LocalDate.now();
        this.estado = EstadoPostulacion.NUEVA; // 👈 inicializa con NUEVA
    }

    public Postulacion() {
        this.fechaPostulacion = LocalDate.now();
        this.estado = EstadoPostulacion.NUEVA;
    }

    // ====== Métodos de negocio ======

    /** Asigna la evaluación realizada a esta postulación. */
    public void asignarEvaluacion(Evaluacion evaluacion) {
        this.evaluacion = evaluacion;
        this.estado = EstadoPostulacion.EN_EVALUACION;
    }

    /** Cambia el estado según los resultados del proceso. */
    public void cambiarEstado(EstadoPostulacion nuevoEstado) {
        this.estado = nuevoEstado;
    }

    // ====== Getters y Setters ======

    public int getIdPostulacion() { return idPostulacion; }

    public void setIdPostulacion(int idPostulacion) { this.idPostulacion = idPostulacion; }

    public LocalDate getFechaPostulacion() { return fechaPostulacion; }

    public void setFechaPostulacion(LocalDate fechaPostulacion) { this.fechaPostulacion = fechaPostulacion; }

    public EstadoPostulacion getEstado() { return estado; }

    public void setEstado(EstadoPostulacion estado) { this.estado = estado; }

    public Postulante getPostulante() { return postulante; }

    public void setPostulante(Postulante postulante) { this.postulante = postulante; }

    public ProcesoSeleccion getProceso() { return proceso; }

    public void setProceso(ProcesoSeleccion proceso) { this.proceso = proceso; }

    public Evaluacion getEvaluacion() { return evaluacion; }

    public void setEvaluacion(Evaluacion evaluacion) { this.evaluacion = evaluacion; }

    /** Alias para compatibilidad con GestorPostulaciones */
    public int getId() {
        return idPostulacion;
    }

    // ====== Métodos auxiliares ======
    @Override
    public String toString() {
        return "Postulacion{id=" + idPostulacion +
                ", estado=" + estado +
                ", postulante=" + (postulante != null ? postulante.getNombreCompleto() : "N/A") +
                ", proceso=" + (proceso != null ? proceso.getPuesto() : "N/A") +
                '}';
    }
}
