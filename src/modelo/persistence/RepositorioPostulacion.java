package modelo.persistence;

import modelo.Postulacion;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioPostulacion extends RepositorioArchivo implements IRepositorio<Postulacion> {

    private static final String SEPARATOR = ";";

    public RepositorioPostulacion() { super("postulaciones.csv"); }

    @Override public Postulacion buscarPorId(int id) {
        return buscarTodos().stream().filter(p -> p.getIdPostulacion() == id).findFirst().orElse(null);
    }

    @Override public List<Postulacion> buscarTodos() {
        return leerArchivo().stream().map(l -> (Postulacion) deserializar(l)).filter(p -> p != null).collect(Collectors.toList());
    }

    @Override public void guardar(Postulacion postulacion) {
        List<Postulacion> postulaciones = buscarTodos();
        if (postulacion.getIdPostulacion() <= 0) postulacion.setIdPostulacion(proximoId());

        postulaciones.removeIf(p -> p.getIdPostulacion() == postulacion.getIdPostulacion());
        postulaciones.add(postulacion);

        escribirArchivo(postulaciones.stream().map(this::serializar).collect(Collectors.toList()));
    }

    @Override public void eliminar(int id) {
        List<Postulacion> postulaciones = buscarTodos();
        postulaciones.removeIf(p -> p.getIdPostulacion() == id);
        escribirArchivo(postulaciones.stream().map(this::serializar).collect(Collectors.toList()));
    }

    @Override public int proximoId() {
        return buscarTodos().stream().mapToInt(Postulacion::getIdPostulacion).max().orElse(0) + 1;
    }

    @Override protected String serializar(Object entidad) {
        Postulacion p = (Postulacion) entidad;
        return p.getIdPostulacion() + SEPARATOR + p.getPostulante().getIdPersona() + SEPARATOR + p.getProceso().getId() + SEPARATOR + p.getEstado().toString();
    }

    @Override protected Object deserializar(String linea) {
        return null;
    }
}