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

    public Evaluacion(int idEvaluacion, String observaciones, int puntaje, Empleado evaluador) {
        this.idEvaluacion = idEvaluacion;
        this.fechaEvaluacion = LocalDate.now();
        this.observaciones = observaciones;
        this.puntaje = puntaje;
        this.evaluador = evaluador;
        this.resultado = ResultadoEntrevista.PENDIENTE; // Se actualiza por setter o lógica más compleja
    }

    public boolean esAprobado() {
        return resultado == ResultadoEntrevista.APROBADO;
    }

    public int getIdEvaluacion() { return idEvaluacion; }
    public void setIdEvaluacion(int idEvaluacion) { this.idEvaluacion = idEvaluacion; }

    public LocalDate getFechaEvaluacion() { return fechaEvaluacion; }
    public void setFechaEvaluacion(LocalDate fechaEvaluacion) { this.fechaEvaluacion = fechaEvaluacion; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public int getPuntaje() { return puntaje; }
    public void setPuntaje(int puntaje) { this.puntaje = puntaje; }

    public ResultadoEntrevista getResultado() { return resultado; }
    public void setResultado(ResultadoEntrevista resultado) { this.resultado = resultado; }

    public Empleado getEvaluador() { return evaluador; }
    public void setEvaluador(Empleado evaluador) { this.evaluador = evaluador; }
}