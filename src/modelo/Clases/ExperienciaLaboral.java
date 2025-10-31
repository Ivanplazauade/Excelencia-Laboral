package modelo.Clases;


public class ExperienciaLaboral {
    private String empresa;
    private int anios;
    private String puesto;

    public ExperienciaLaboral(String empresa, int anios, String puesto) {
        this.empresa = empresa;
        this.anios = anios;
        this.puesto = puesto;
    }

    public int getAnios() { return anios; }

    @Override
    public String toString() {
        return puesto + " en " + empresa + " (" + anios + " años)";
    }
}
