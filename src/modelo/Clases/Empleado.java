package modelo.Clases;

import java.util.List;

public class Empleado extends Persona{

    //Atributos

    private int Legajo;
    private String puesto;
    private double Salario;
    private Usuario usuario;

    //Metodos

    public void crearProcesoSeleccion(Cliente c, String puesto, List<Skill> skillsRequeridas){};
    public void evaluarPostulante(Postulacion postulacion, String observaciones, int puntaje){};


    @Override
    public String getNombreCompleto() {
        return "";
    }

    @Override
    public String getTipoPersona() {
        return "";
    }

    //Constructores

    public Empleado() {
    }

    public Empleado(int legajo, String puesto, double salario, Usuario usuario) {
        Legajo = legajo;
        this.puesto = puesto;
        Salario = salario;
        this.usuario = usuario;
    }

    //Getters y setters
    public int getLegajo() {
        return Legajo;
    }

    public void setLegajo(int legajo) {
        Legajo = legajo;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public double getSalario() {
        return Salario;
    }

    public void setSalario(double salario) {
        Salario = salario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
