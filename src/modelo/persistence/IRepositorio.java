package modelo.persistence;

import java.util.List;

public interface IRepositorio<T> {

    T buscarPorId(int id);

    List<T> buscarTodos();

    void guardar(T entidad);

    void eliminar(int id);

    int proximoId();
}