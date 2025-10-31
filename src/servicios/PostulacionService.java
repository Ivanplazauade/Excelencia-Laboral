package servicios;

import modelo.Clases.Empleado;
import modelo.Clases.Evaluacion;
import modelo.Clases.Postulante;
import modelo.Clases.ProcesoSeleccion;
import modelo.Postulacion;
import modelo.enums.EstadoPostulacion;
import modelo.enums.EstadoVacante;
import modelo.persistence.*;
import excepciones.*;

import java.util.List;

public class PostulacionService {

    private static PostulacionService instancia;

    private final IRepositorio<Postulacion> postulacionRepositorio;
    private final IRepositorio<ProcesoSeleccion> procesoRepositorio;
    private final IRepositorio<Postulante> postulanteRepositorio;

    private PostulacionService() {
        this.postulacionRepositorio = new RepositorioPostulacion();
        this.procesoRepositorio = new RepositorioProcesoSeleccion();
        this.postulanteRepositorio = new RepositorioPostulante();
    }

    public static PostulacionService getInstancia() {
        if (instancia == null) {
            instancia = new PostulacionService();
        }
        return instancia;
    }

    public void crearPostulacion(int postulanteId, int procesoId)
            throws CandidatoInactivoException, VacanteCerradaException, PostulacionDuplicadaException {

        Postulante postulante = postulanteRepositorio.buscarPorId(postulanteId);
        ProcesoSeleccion proceso = procesoRepositorio.buscarPorId(procesoId);

        if (postulante == null || proceso == null) {
            throw new IllegalArgumentException("Postulante o Proceso no encontrado.");
        }

        if (!postulante.isActivo()) {
            throw new CandidatoInactivoException("El candidato no está activo.");
        }

        if (proceso.getEstado() != EstadoVacante.ABIERTA) {
            throw new VacanteCerradaException("La vacante está cerrada o en proceso.", procesoId);
        }

        boolean yaExiste = postulacionRepositorio.buscarTodos().stream()
                .anyMatch(p -> p.getPostulante().getIdPersona() == postulanteId &&
                        p.getProceso().getId() == procesoId &&
                        p.getEstado() != EstadoPostulacion.DESCARTADA);

        if (yaExiste) {
            throw new PostulacionDuplicadaException("El candidato ya está postulado.", postulanteId, procesoId);
        }

        int nuevoId = postulacionRepositorio.proximoId();
        Postulacion nuevaPostulacion = new Postulacion(nuevoId, postulante, proceso);
        postulacionRepositorio.guardar(nuevaPostulacion);
    }

    public void registrarEvaluacion(int postulacionId, String observaciones, int puntaje, Empleado evaluador) {
        Postulacion postulacion = postulacionRepositorio.buscarPorId(postulacionId);

        if (postulacion == null) {
            throw new IllegalArgumentException("Postulación no encontrada.");
        }

        Evaluacion nuevaEvaluacion = new Evaluacion(0, observaciones, puntaje, evaluador);

        postulacion.agregarEvaluacion(nuevaEvaluacion);
        postulacionRepositorio.guardar(postulacion);
    }

    public List<Postulacion> listarTodos() {
        return postulacionRepositorio.buscarTodos();
    }
}