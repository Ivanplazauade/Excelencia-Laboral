package servicios;

import modelo.Clases.Postulante;
import modelo.persistence.IRepositorio;
import modelo.persistence.RepositorioPostulante;
import java.util.List;
import java.util.stream.Collectors;

public class PostulanteService {

    private static PostulanteService instancia;
    private final IRepositorio<Postulante> postulanteRepositorio;

    private PostulanteService() {
        this.postulanteRepositorio = new RepositorioPostulante();
    }

    public static PostulanteService getInstancia() {
        if (instancia == null) {
            instancia = new PostulanteService();
        }
        return instancia;
    }

    public void guardar(Postulante p) {
        postulanteRepositorio.guardar(p);
    }

    public List<Postulante> buscarPorSkill(String skillName) {
        List<Postulante> todos = postulanteRepositorio.buscarTodos();

        return todos.stream()
                .filter(p -> p.getSkills().stream()
                        .anyMatch(s -> s.getNombre().equalsIgnoreCase(skillName)))
                .collect(Collectors.toList());
    }

    public List<Postulante> listarTodos() {
        return postulanteRepositorio.buscarTodos();
    }

    public Postulante buscarPorId(int id) {
        return postulanteRepositorio.buscarPorId(id);
    }
}