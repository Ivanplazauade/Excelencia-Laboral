package excepciones;

public class CandidatoInactivoException extends Exception {
    public CandidatoInactivoException(String mensaje) {
        super(mensaje);
    }
}