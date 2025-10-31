package modelo.persistence;

import modelo.Clases.*;
import modelo.enums.NivelSkill;
import excepciones.EdadInvalidaException;
import excepciones.EmailInvalidoException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioPostulante extends RepositorioArchivo implements IRepositorio<Postulante> {

    private static final String SEPARATOR = ";";
    private static final String LIST_SEP = "|"; // Separador de items en listas (Skills, Experiencias)

    public RepositorioPostulante() { super("postulantes.csv"); }

    @Override public Postulante buscarPorId(int id) {
        return buscarTodos().stream().filter(p -> p.getIdPersona() == id).findFirst().orElse(null);
    }

    @Override public List<Postulante> buscarTodos() {
        return leerArchivo().stream().map(l -> (Postulante) deserializar(l)).filter(p -> p != null).collect(Collectors.toList());
    }

    @Override public void guardar(Postulante postulante) {
        List<Postulante> postulantes = buscarTodos();
        if (postulante.getIdPersona() <= 0) postulante.setIdPersona(proximoId());

        postulantes.removeIf(p -> p.getIdPersona() == postulante.getIdPersona());
        postulantes.add(postulante);

        escribirArchivo(postulantes.stream().map(this::serializar).collect(Collectors.toList()));
    }

    @Override public void eliminar(int id) {
        List<Postulante> postulantes = buscarTodos();
        postulantes.removeIf(p -> p.getIdPersona() == id);
        escribirArchivo(postulantes.stream().map(this::serializar).collect(Collectors.toList()));
    }

    @Override public int proximoId() {
        return buscarTodos().stream().mapToInt(Postulante::getIdPersona).max().orElse(0) + 1;
    }

    @Override
    protected String serializar(Object entidad) {
        Postulante p = (Postulante) entidad;

        // Serializa Skills: Nombre1,Nivel1|Nombre2,Nivel2
        String skillsStr = p.getSkills().stream()
                .map(s -> s.getNombre() + "," + s.getNivelRequerido())
                .collect(Collectors.joining(LIST_SEP));

        // Serializa Experiencias: Empresa1,Años1,Puesto1|Empresa2,Años2,Puesto2
        String expStr = p.getExperiencias().stream()
                .map(e -> e.getEmpresa() + "," + e.getAnios() + "," + e.getPuesto())
                .collect(Collectors.joining(LIST_SEP));

        // ID;Nombre;Apellido;DNI;Email;FechaNac;Activo;CV;Skills;Experiencias;DirCalle,DirCiudad
        return p.getIdPersona() + SEPARATOR + p.getNombre() + SEPARATOR + p.getApellido() + SEPARATOR +
                p.getDni() + SEPARATOR + p.getEmail() + SEPARATOR + p.getFechaNacimiento().toString() + SEPARATOR +
                p.isActivo() + SEPARATOR + p.getCv() + SEPARATOR + skillsStr + SEPARATOR + expStr + SEPARATOR +
                p.getDireccion().getCalle() + "," + p.getDireccion().getCiudad(); // Simplificado de Dirección
    }

    @Override
    protected Object deserializar(String linea) {
        String[] partes = linea.split(SEPARATOR);
        if (partes.length < 11) return null; // Validación básica

        try {
            int id = Integer.parseInt(partes[0]);
            LocalDate fechaNac = LocalDate.parse(partes[5]);
            boolean activo = Boolean.parseBoolean(partes[6]);

            String[] dirParts = partes[10].split(",");
            Direccion d = new Direccion(dirParts[0], dirParts[1], "N/A", "N/A", "N/A");

            Postulante p = new Postulante(id, partes[1], partes[2], partes[3], partes[4], null, fechaNac, d, partes[7]);
            p.setActivo(activo);

            // Deserializar Skills
            if (!partes[8].isEmpty()) {
                Arrays.stream(partes[8].split("\\" + LIST_SEP))
                        .map(s -> s.split(","))
                        .forEach(s -> p.agregarSkill(s[0], NivelSkill.valueOf(s[1])));
            }

            // Deserializar Experiencias
            if (!partes[9].isEmpty()) {
                Arrays.stream(partes[9].split("\\" + LIST_SEP))
                        .map(e -> e.split(","))
                        .forEach(e -> p.agregarExperiencia(new ExperienciaLaboral(e[0], Integer.parseInt(e[1]), e[2])));
            }
            return p;
        } catch (EdadInvalidaException | EmailInvalidoException | RuntimeException e) {
            System.err.println("Error al deserializar Postulante: " + e.getMessage());
            return null;
        }
    }
}