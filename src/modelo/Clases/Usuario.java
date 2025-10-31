package modelo.Clases;

import modelo.enums.Rol;

public class Usuario {
    private String username;
    private String password;
    private Rol rol;
    private boolean activo;

    public Usuario() {
    }

    public Usuario(String username, String password, Rol rol, boolean activo) {
        this.username = username;
        this.password = password;
        this.rol = rol;
        this.activo = activo;
    }

    public boolean autenticar(String user, String pass){
        return this.username.equals(user) && this.password.equals(pass) && this.activo;
    }

    public boolean tienePermiso(String accion){
        return true; // Lógica de permisos dependerá del Enum Rol y la acción
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}