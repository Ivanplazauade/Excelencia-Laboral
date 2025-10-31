package servicios;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReporteService {

    private static ReporteService instancia;
    private final PostulacionService postulacionService;

    private ReporteService() {
        this.postulacionService = PostulacionService.getInstancia();
    }

    public static ReporteService getInstancia() {
        if (instancia == null) {
            instancia = new ReporteService();
        }
        return instancia;
    }

    public Map<String, Long> getPostulacionesPorEstado() {
        return postulacionService.listarTodos().stream()
                .collect(Collectors.groupingBy(
                        p -> p.getEstado().toString(), Collectors.counting()
                ));
    }
}