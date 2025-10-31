package modelo.persistence;

import modelo.Clases.Empleado;
import modelo.Clases.Direccion;
import modelo.Clases.Usuario;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioEmpleado extends RepositorioArchivo implements IRepositorio<Empleado> {

    private static final String SEPARATOR = ";";

    public RepositorioEmpleado() { super("empleados.csv"); }

    @Override public Empleado buscarPorId(int id) {
        return buscarTodos().stream().filter(e -> e.getIdPersona() == id).findFirst().orElse(null);
    }

    @Override public List<Empleado> buscarTodos() {
        return leerArchivo().stream().map(l -> (Empleado) deserializar(l)).filter(e -> e != null).collect(Collectors.toList());
    }

    @Override public void guardar(Empleado empleado) {
        List<Empleado> empleados = buscarTodos();
        if (empleado.getIdPersona() <= 0) empleado.setIdPersona(proximoId());

        empleados.removeIf(e -> e.getIdPersona() == empleado.getIdPersona());
        empleados.add(empleado);

        escribirArchivo(empleados.stream().map(this::serializar).collect(Collectors.toList()));
    }

    @Override public void eliminar(int id) {
        List<Empleado> empleados = buscarTodos();
        empleados.removeIf(e -> e.getIdPersona() == id);
        escribirArchivo(empleados.stream().map(this::serializar).collect(Collectors.toList()));
    }

    @Override public int proximoId() {
        return buscarTodos().stream().mapToInt(Empleado::getIdPersona).max().orElse(0) + 1;
    }

    @Override protected String serializar(Object entidad) {
        Empleado e = (Empleado) entidad;
        return e.getIdPersona() + SEPARATOR + e.getLegajo() + SEPARATOR + e.getNombre() + SEPARATOR + e.getPuesto() + SEPARATOR + e.getSalario() + SEPARATOR + e.getEmail();
    }

    @Override protected Object deserializar(String linea) {
        String[] partes = linea.split(SEPARATOR);
        try {
            int id = Integer.parseInt(partes[0]);
            int legajo = Integer.parseInt(partes[1]);
            double salario = Double.parseDouble(partes[4]);
            // Nota: La deserialización completa requiere reconstruir Direccion y Usuario.
            return new Empleado(id, partes[2], "Apellido_N/A", "DNI_N/A", partes[5], "Tel_N/A", new Direccion("N/A", "N/A", "N/A", "N/A", "N/A"), legajo, partes[3], salario, new Usuario());
        } catch (Exception e) { return null; }
    }
}