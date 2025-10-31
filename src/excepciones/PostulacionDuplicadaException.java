package excepciones;

public class PostulacionDuplicadaException extends RuntimeException {
    public PostulacionDuplicadaException(String message) {
        super(message);
    }
}
