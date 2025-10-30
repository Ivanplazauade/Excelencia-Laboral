package Clases;

import java.time.LocalDate;
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

}
