package Clases;

public class Direccion {
    private final String calle;
    private final String ciudad;
    private final String provincia;
    private final String codigoPostal;


    //Constructores
    public Direccion(String calle, String ciudad, String provincia, String codigoPostal) {
        this.calle = calle;
        this.ciudad = ciudad;
        this.provincia = provincia;
        this.codigoPostal = codigoPostal;
    }
    //Getters sin setters porque es inmutable

    public String getCiudad() {
        return ciudad;
    }

    public String getProvincia() {
        return provincia;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public String getCalle() {
        return calle;
    }
}
