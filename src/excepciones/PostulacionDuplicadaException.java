package excepciones;

public class PostulacionDuplicadaException extends Exception {
    private final int idPostulante;
    private final int idProceso;

    public PostulacionDuplicadaException(String mensaje, int idPostulante, int idProceso) {
        super(mensaje);
        this.idPostulante = idPostulante;
        this.idProceso = idProceso;
    }

    public int getIdPostulante() {
        return idPostulante;
    }

    public int getIdProceso() {
        return idProceso;
    }
}