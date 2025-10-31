package modelo.Clases;

import excepciones.EdadInvalidaException;
import excepciones.EmailInvalidoException;
import modelo.enums.NivelSkill;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Postulante extends Persona {
    private LocalDate fechaNacimiento;
    private List<Skill> skills;
    private List<ExperienciaLaboral> experiencias;
    private boolean activo = true;
    private String cv;

    public Postulante(int id, String nombre, String apellido, String dni, String email, String telefono,
                      LocalDate fechaNacimiento, Direccion direccion, String cv)
            throws EdadInvalidaException, EmailInvalidoException {
        // Llama al constructor completo de Persona
        super(id, nombre, apellido, dni, email, telefono, direccion);
        validarEdad(fechaNacimiento);
        validarEmail(email);
        this.fechaNacimiento = fechaNacimiento;
        this.cv = cv;
        this.skills = new ArrayList<>();
        this.experiencias = new ArrayList<>();
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

    @Override
    public String getNombreCompleto() {
        return getNombre() + " " + getApellido();
    }

    @Override
    public String getTipoPersona() { return "Postulante"; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public String getCv() { return cv; }
    public void setCv(String cv) { this.cv = cv; }

    public List<Skill> getSkills() { return Collections.unmodifiableList(skills); }
    public List<ExperienciaLaboral> getExperiencias() { return Collections.unmodifiableList(experiencias); }
}