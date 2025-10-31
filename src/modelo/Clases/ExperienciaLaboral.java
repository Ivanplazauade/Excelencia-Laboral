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

    @Override
    public String toString() {
        return puesto + " en " + empresa + " (" + anios + " años)";
    }

    public String getEmpresa() { return empresa; }
    public void setEmpresa(String empresa) { this.empresa = empresa; }

    public int getAnios() { return anios; }
    public void setAnios(int anios) { this.anios = anios; }

    public String getPuesto() { return puesto; }
    public void setPuesto(String puesto) { this.puesto = puesto; }
}