package modelo;

import modelo.Clases.Evaluacion;
import modelo.Clases.Postulante;
import modelo.Clases.ProcesoSeleccion;
import modelo.enums.EstadoPostulacion;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Postulacion {

    private int idPostulacion;
    private LocalDate fechaPostulacion;
    private EstadoPostulacion estado;
    private Postulante postulante;
    private ProcesoSeleccion proceso;
    private List<Evaluacion> evaluaciones; // Cambiado a 1..*

    public Postulacion(int idPostulacion, Postulante postulante, ProcesoSeleccion proceso) {
        this.idPostulacion = idPostulacion;
        this.postulante = postulante;
        this.proceso = proceso;
        this.fechaPostulacion = LocalDate.now();
        this.estado = EstadoPostulacion.NUEVA;
        this.evaluaciones = new ArrayList<>();
    }

    public void agregarEvaluacion(Evaluacion evaluacion) {
        this.evaluaciones.add(evaluacion);
        this.estado = EstadoPostulacion.EN_EVALUACION;
    }

    public void cambiarEstado(EstadoPostulacion nuevoEstado) {
        this.estado = nuevoEstado;
    }

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

    public List<Evaluacion> getEvaluaciones() { return evaluaciones; }
    public void setEvaluaciones(List<Evaluacion> evaluaciones) { this.evaluaciones = evaluaciones; }

    @Override
    public String toString() {
        return "Postulacion{id=" + idPostulacion +
                ", estado=" + estado +
                ", postulante=" + (postulante != null ? postulante.getNombreCompleto() : "N/A") +
                ", proceso=" + (proceso != null ? proceso.getPuesto() : "N/A") +
                '}';
    }
}