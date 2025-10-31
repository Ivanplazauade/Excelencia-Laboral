package servicios;

import modelo.Clases.Cliente;
import modelo.Clases.Empleado;
import modelo.Clases.ProcesoSeleccion;
import modelo.Clases.Skill;
import modelo.persistence.IRepositorio;
import modelo.persistence.RepositorioProcesoSeleccion;
import java.util.List;

public class ProcesoSeleccionService {

    private static ProcesoSeleccionService instancia;
    private final IRepositorio<ProcesoSeleccion> procesoRepositorio;

    private ProcesoSeleccionService() {
        this.procesoRepositorio = new RepositorioProcesoSeleccion();
    }

    public static ProcesoSeleccionService getInstancia() {
        if (instancia == null) {
            instancia = new ProcesoSeleccionService();
        }
        return instancia;
    }

    public ProcesoSeleccion crearProceso(String puesto, Cliente cliente, Empleado responsable, List<Skill> requeridas) {
        int id = procesoRepositorio.proximoId();
        ProcesoSeleccion proceso = new ProcesoSeleccion(id, puesto, "Solicitud inicial", requeridas);
        proceso.setCliente(cliente);
        proceso.setResponsable(responsable);

        procesoRepositorio.guardar(proceso);
        return proceso;
    }

    public void cerrarProceso(int idProceso) {
        ProcesoSeleccion proceso = procesoRepositorio.buscarPorId(idProceso);
        if (proceso != null) {
            proceso.cerrarProceso();
            procesoRepositorio.guardar(proceso);
        }
    }

    public List<ProcesoSeleccion> listarTodos() {
        return procesoRepositorio.buscarTodos();
    }
}