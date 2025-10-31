package modelo.Clases;

import modelo.enums.ResultadoEntrevista;

import java.time.LocalDate;

public class Evaluacion {
    private int idEvaluacion;
    private LocalDate fechaEvaluacion;
    private String observaciones;
    private int puntaje;
    private ResultadoEntrevista resultado;
    private Empleado evaluador;

    public Evaluacion(int idPostulacion, String notas, int puntaje, Empleado reclutador) {
    }

    public int getIdEvaluacion() {
        return idEvaluacion;
    }

    public void setIdEvaluacion(int idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }

    public LocalDate getFechaEvaluacion() {
        return fechaEvaluacion;
    }

    public void setFechaEvaluacion(LocalDate fechaEvaluacion) {
        this.fechaEvaluacion = fechaEvaluacion;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public ResultadoEntrevista getResultado() {
        return resultado;
    }

    public void setResultado(ResultadoEntrevista resultado) {
        this.resultado = resultado;
    }

    public Empleado getEvaluador() {
        return evaluador;
    }

    public void setEvaluador(Empleado evaluador) {
        this.evaluador = evaluador;
    }
    public boolean esAprobado() {
        return this.resultado == ResultadoEntrevista.APROBADO;
    }


}
