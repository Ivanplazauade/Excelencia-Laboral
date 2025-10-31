package modelo.Clases;

public class Direccion {
    private final String calle;
    private final String ciudad;
    private final String provincia;
    private final String numero;
    private final String codigoPostal;

    public Direccion(String calle, String ciudad, String provincia, String numero, String codigoPostal)  {
        this.calle = calle;
        this.ciudad = ciudad;
        this.provincia = provincia;
        this.numero = numero;
        this.codigoPostal = codigoPostal;
    }

    @Override
    public String toString() {
        return calle + " N°" + numero + ", " + ciudad + " (" + provincia + ") CP:" + codigoPostal;
    }

    public String getCalle() { return calle; }
    public String getCiudad() { return ciudad; }
    public String getProvincia() { return provincia; }
    public String getCodigoPostal() { return codigoPostal; }
    public String getNumero() { return numero; }
}