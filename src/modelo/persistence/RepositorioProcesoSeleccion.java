package modelo.persistence;

import modelo.Clases.*;
import modelo.enums.EstadoVacante;
import modelo.enums.NivelSkill;
import servicios.PostulacionService; // Para cargar postulaciones
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioProcesoSeleccion extends RepositorioArchivo implements IRepositorio<ProcesoSeleccion> {

    private static final String SEPARATOR = ";";
    private static final String SKILL_SEP = "|";

    public RepositorioProcesoSeleccion() { super("procesos.csv"); }

    @Override public ProcesoSeleccion buscarPorId(int id) {
        return buscarTodos().stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    @Override public List<ProcesoSeleccion> buscarTodos() {
        return leerArchivo().stream().map(l -> (ProcesoSeleccion) deserializar(l)).filter(p -> p != null).collect(Collectors.toList());
    }

    @Override public void guardar(ProcesoSeleccion proceso) {
        List<ProcesoSeleccion> procesos = buscarTodos();
        if (proceso.getId() <= 0) proceso.setId(proximoId());

        procesos.removeIf(p -> p.getId() == proceso.getId());
        procesos.add(proceso);

        escribirArchivo(procesos.stream().map(this::serializar).collect(Collectors.toList()));
    }

    @Override public void eliminar(int id) {
        List<ProcesoSeleccion> procesos = buscarTodos();
        procesos.removeIf(p -> p.getId() == id);
        escribirArchivo(procesos.stream().map(this::serializar).collect(Collectors.toList()));
    }

    @Override public int proximoId() {
        return buscarTodos().stream().mapToInt(ProcesoSeleccion::getId).max().orElse(0) + 1;
    }

    @Override
    protected String serializar(Object entidad) {
        ProcesoSeleccion p = (ProcesoSeleccion) entidad;

        // Serializa Skills: Nombre1,Nivel1|Nombre2,Nivel2
        String skillsStr = p.getSkillsRequeridas().stream()
                .map(s -> s.getNombre() + "," + s.getNivelRequerido())
                .collect(Collectors.joining(SKILL_SEP));

        // ID;Puesto;Descripcion;FechaApertura;Estado;ClienteID;ResponsableID;Skills;Vacantes
        return p.getId() + SEPARATOR + p.getPuesto() + SEPARATOR + p.getDescripcion() + SEPARATOR +
                p.getFechaApertura().toString() + SEPARATOR + p.getEstado().toString() + SEPARATOR +
                (p.getCliente() != null ? p.getCliente().getIdPersona() : "0") + SEPARATOR +
                (p.getResponsable() != null ? p.getResponsable().getIdPersona() : "0") + SEPARATOR +
                skillsStr + SEPARATOR + p.getVacantesDisponibles();
    }

    @Override
    protected Object deserializar(String linea) {
        String[] partes = linea.split(SEPARATOR);
        if (partes.length < 9) return null;

        try {
            int id = Integer.parseInt(partes[0]);
            LocalDate fechaApertura = LocalDate.parse(partes[3]);
            EstadoVacante estado = EstadoVacante.valueOf(partes[4]);
            int clienteId = Integer.parseInt(partes[5]);
            int responsableId = Integer.parseInt(partes[6]);
            int vacantes = Integer.parseInt(partes[8]);

            // Reconstruir Skills
            List<Skill> requeridas = Arrays.stream(partes[7].split("\\" + SKILL_SEP))
                    .filter(s -> !s.isEmpty())
                    .map(s -> s.split(","))
                    .map(s -> new Skill(s[0], NivelSkill.valueOf(s[1])))
                    .collect(Collectors.toList());

            ProcesoSeleccion p = new ProcesoSeleccion(id, partes[1], partes[2], requeridas);
            p.setFechaApertura(fechaApertura);
            p.setEstado(estado);
            p.setVacantesDisponibles(vacantes);

            // Cargar Cliente y Responsable (Asumiendo que RepositorioCliente y RepositorioEmpleado existen)
            // Aquí se simula la carga, en un entorno real se usarían sus respectivos repositorios.
            // Cliente cliente = new RepositorioCliente().buscarPorId(clienteId);
            // Empleado responsable = new RepositorioEmpleado().buscarPorId(responsableId);

            // Simulamos objetos Cliente/Empleado solo para que la clase funcione
            Cliente cliente = new Cliente(clienteId, "Cliente", "C", "cliente@n/a.com", null, "Razon Social", "CUIT");
            Empleado responsable = new Empleado(responsableId, "Resp", "R", "resp@n/a.com", 0);

            p.setCliente(cliente);
            p.setResponsable(responsable);

            // Cargar Postulaciones (si fuera necesario, se haría aquí a través de RepositorioPostulacion)

            return p;
        } catch (Exception e) {
            System.err.println("Error al deserializar Proceso: " + e.getMessage());
            return null;
        }
    }
}