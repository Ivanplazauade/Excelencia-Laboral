package persistencia;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Interface genérica que define el contrato para operaciones de persistencia.
 * Separa la lógica de almacenamiento del modelo de negocio (principio SRP y DIP).
 */
public interface IRepositorio<T> {

    /**
     * Guarda una entidad en el archivo o fuente de datos.
     */
    void guardar(T entidad) throws IOException;

    /**
     * Devuelve todas las entidades almacenadas.
     */
    List<T> listar() throws IOException;

    /**
     * Busca una entidad por su identificador (si aplica).
     */
    Optional<T> buscarPorId(int id) throws IOException;
}
