package modelo.Clases;

public abstract class Persona {
    protected int idPersona;
    protected String nombre;
    protected String apellido;
    protected String dni;
    protected String email;
    protected String telefono;
    protected Direccion direccion;

    public Persona() {
    }

    public Persona(int idPersona, String nombre, String apellido, String dni, String email, String telefono, Direccion direccion) {
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public abstract String getNombreCompleto();
    public abstract String getTipoPersona();

    public int getIdPersona() { return idPersona; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getDni() { return dni; }
    public String getEmail() { return email; }
    public String getTelefono() { return telefono; }
    public Direccion getDireccion() { return direccion; }

    public void setIdPersona(int idPersona) { this.idPersona = idPersona; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public void setDni(String dni) { this.dni = dni; }
    public void setEmail(String email) { this.email = email; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setDireccion(Direccion direccion) { this.direccion = direccion; }
}