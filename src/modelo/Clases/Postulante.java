package modelo.Clases;

import excepciones.EdadInvalidaException;
import excepciones.EmailInvalidoException;
import modelo.enums.NivelSkill;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class Postulante extends Persona {
    private String dni;
    private LocalDate fechaNacimiento;
    private List<Skill> skills;
    private List<ExperienciaLaboral> experiencias;
    private boolean activo = true;
    private String cv;

    public Postulante(int id, String nombre, String apellido, String email, String dni,
                      LocalDate fechaNacimiento, Direccion direccion, String cv)
            throws EdadInvalidaException, EmailInvalidoException {
        super(id, nombre, apellido, email, direccion);
        validarEdad(fechaNacimiento);
        validarEmail(email);
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.cv = cv;
        this.skills = new ArrayList<>();
        this.experiencias = new ArrayList<>();
    }

    @Override
    public String getDni() {
        return dni;
    }

    @Override
    public void setDni(String dni) {
        this.dni = dni;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public void setExperiencias(List<ExperienciaLaboral> experiencias) {
        this.experiencias = experiencias;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    private void validarEdad(LocalDate fecha) throws EdadInvalidaException {
        int edad = Period.between(fecha, LocalDate.now()).getYears();
        if (edad < 18) throw new EdadInvalidaException("Debe ser mayor de 18 años");
    }

    private void validarEmail(String email) throws EmailInvalidoException {
        if (!email.contains("@") || !email.contains(".")) {
            throw new EmailInvalidoException("Email inválido");
        }
    }

    public void agregarSkill(String nombre, NivelSkill nivel) {
        skills.add(new Skill(nombre, nivel));
    }

    public void agregarExperiencia(ExperienciaLaboral exp) {
        experiencias.add(exp);
    }

    public boolean isActivo() { return activo; }

    public List<Skill> getSkills() { return skills; }

    public List<ExperienciaLaboral> getExperiencias() { return experiencias; }

    @Override
    public String getNombreCompleto() {
        return getNombre()+" "+getApellido();
    }

    @Override
    public String getTipoPersona() { return "Postulante"; }

}
