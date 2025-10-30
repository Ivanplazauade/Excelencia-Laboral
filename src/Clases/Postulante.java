package Clases;

import java.util.List;

public class Postulante extends Persona{
    private List<Skill> skills;
    private List<Postulacion> postulaciones;
    private String curriculumPath;

    //Metodos
    public void postularse(ProcesoSeleccion proceso){};
    public void agregarSkill(Skill skill){};
    public void verHistorialPostulaciones(){};

    @Override
    public String getNombreCompleto() {
        return "";
    }

    @Override
    public String getTipoPersona() {
        return "";
    }

    //Constructores

    //Getters and Setters


}
