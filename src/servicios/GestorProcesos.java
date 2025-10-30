package servicios;

import modelo.*;
import modelo.Clases.ProcesoSeleccion;
import modelo.Clases.Skill;
import modelo.enums.*;
import excepciones.VacanteCerradaException;

import java.util.*;

public class GestorProcesos {
    private List<ProcesoSeleccion> procesos = new ArrayList<>();

    public ProcesoSeleccion crearProceso(int id, String puesto, String descripcion,
                                         List<Skill> skills, String ubicacion,
                                         double salarioMin, double salarioMax, int vacantes) {
        ProcesoSeleccion p = new ProcesoSeleccion(id, puesto, descripcion, skills);
        p.setUbicacion(ubicacion);
        p.setRangoSalarial(salarioMin, salarioMax);
        p.setVacantesDisponibles(vacantes);
        procesos.add(p);
        System.out.println("✅ Vacante creada: " + puesto + " (" + vacantes + " disponibles)");
        return p;
    }

    public List<ProcesoSeleccion> listarAbiertas() {
        return procesos.stream()
                .filter(p -> p.getEstado() == EstadoVacante.ABIERTA)
                .toList();
    }
}

