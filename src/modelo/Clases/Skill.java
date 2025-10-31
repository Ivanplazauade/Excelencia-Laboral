package modelo.Clases;

import modelo.enums.NivelSkill;

public class Skill {
    private int idSkill;
    private String nombre;
    private String descripcion;
    private NivelSkill nivelRequerido;

    public Skill(String nombre, NivelSkill nivel) {
    }

    public Skill(String nombre, int idSkill, String descripcion, NivelSkill nivelRequerido) {
        this.nombre = nombre;
        this.idSkill = idSkill;
        this.descripcion = descripcion;
        this.nivelRequerido = nivelRequerido;
    }

    public int getIdSkill() {
        return idSkill;
    }

    public void setIdSkill(int idSkill) {
        this.idSkill = idSkill;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public NivelSkill getNivelRequerido() {
        return nivelRequerido;
    }

    public void setNivelRequerido(NivelSkill nivelRequerido) {
        this.nivelRequerido = nivelRequerido;
    }
}
