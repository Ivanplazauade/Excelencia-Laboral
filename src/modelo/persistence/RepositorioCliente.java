package modelo.persistence;

import modelo.Clases.Cliente;
import modelo.Clases.Direccion;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioCliente extends RepositorioArchivo implements IRepositorio<Cliente> {

    private static final String SEPARATOR = ";";

    public RepositorioCliente() {
        super("clientes.csv");
    }

    @Override
    public Cliente buscarPorId(int id) {
        return buscarTodos().stream()
                .filter(c -> c.getIdPersona() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Cliente> buscarTodos() {
        List<Cliente> clientes = new ArrayList<>();
        for (String linea : leerArchivo()) {
            Cliente c = (Cliente) deserializar(linea);
            if (c != null) clientes.add(c);
        }
        return clientes;
    }

    @Override
    public void guardar(Cliente cliente) {
        List<Cliente> clientes = buscarTodos();
        boolean existe = false;

        if (cliente.getIdPersona() <= 0) {
            cliente.setIdPersona(proximoId());
        } else {
            for (int i = 0; i < clientes.size(); i++) {
                if (clientes.get(i).getIdPersona() == cliente.getIdPersona()) {
                    clientes.set(i, cliente);
                    existe = true;
                    break;
                }
            }
            if (!existe) clientes.add(cliente);
        }

        List<String> lineas = clientes.stream()
                .map(this::serializar)
                .collect(Collectors.toList());

        escribirArchivo(lineas);
    }

    @Override
    public void eliminar(int id) {
        List<Cliente> clientes = buscarTodos();
        clientes.removeIf(c -> c.getIdPersona() == id);

        List<String> lineas = clientes.stream()
                .map(this::serializar)
                .collect(Collectors.toList());

        escribirArchivo(lineas);
    }

    @Override
    public int proximoId() {
        return buscarTodos().stream()
                .mapToInt(Cliente::getIdPersona)
                .max()
                .orElse(0) + 1;
    }

    @Override
    protected String serializar(Object entidad) {
        Cliente c = (Cliente) entidad;
        Direccion d = c.getDireccion();
        String dirString = d != null ? String.format("%s,%s,%s", d.getCalle(), d.getCiudad(), d.getProvincia()) : "N/A,N/A,N/A";

        return c.getIdPersona() + SEPARATOR +
                c.getRazonSocial() + SEPARATOR +
                c.getCuit() + SEPARATOR +
                c.getNombre() + SEPARATOR +
                c.getApellido() + SEPARATOR +
                c.getEmail() + SEPARATOR +
                dirString;
    }

    @Override
    protected Object deserializar(String linea) {
        String[] partes = linea.split(SEPARATOR);
        try {
            int id = Integer.parseInt(partes[0]);
            String[] dirParts = partes[6].split(",");
            Direccion d = new Direccion(dirParts[0], dirParts[1], dirParts[2], "N/A", "N/A");

            return new Cliente(id, partes[3], partes[4], partes[5], d, partes[1], partes[2]);
        } catch (Exception e) {
            System.err.println("Error al deserializar Cliente: " + linea);
            return null;
        }
    }
}