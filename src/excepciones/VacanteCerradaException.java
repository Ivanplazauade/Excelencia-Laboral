package excepciones;

public class VacanteCerradaException extends Exception {
    private final int idProceso;

    public VacanteCerradaException(String mensaje, int idProceso) {
        super(mensaje);
        this.idProceso = idProceso;
    }

    public int getIdProceso() {
        return idProceso;
    }
}