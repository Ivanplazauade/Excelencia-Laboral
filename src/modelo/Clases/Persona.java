package modelo.Clases;

public abstract class  Persona {
    //Atributos de cada persona
    protected int idPersona;
    protected String nombre;
    protected String apellido;
    protected String dni;
    protected String email;
    protected String telefono;
    protected Direccion direccion;

    public Persona(int id, String nombre, String apellido, String email, Direccion direccion) {
    }


    //Metodos posibles
    public abstract String getNombreCompleto();
    public abstract String getTipoPersona();

    //Constructores

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

    //Getters y setters


    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }
}
