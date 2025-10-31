package excepciones;

public class CandidatoInactivoException extends RuntimeException {
    public CandidatoInactivoException(String message) {
        super(message);
    }
}
