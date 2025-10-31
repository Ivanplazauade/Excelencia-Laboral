package servicios;

import modelo.*;
import modelo.Clases.Empleado;
import modelo.Clases.Evaluacion;
import modelo.Clases.Postulante;
import modelo.Clases.ProcesoSeleccion;
import modelo.Postulacion;
import modelo.enums.*;
import excepciones.*;

public class GestorPostulaciones {

    public Postulacion postular(Postulante p, ProcesoSeleccion proceso, int idPostulacion)
            throws CandidatoInactivoException, VacanteCerradaException, PostulacionDuplicadaException {

        if (!p.isActivo()) {
            throw new CandidatoInactivoException("El candidato está inactivo");
        }
        if (proceso.getEstado() != EstadoVacante.ABIERTA) {
            throw new VacanteCerradaException("La vacante no está abierta");
        }

        /// Evitar duplicación de postulación (ideal comparar por id)
        boolean yaPostulado = proceso.getPostulaciones().stream()
                .anyMatch(pos -> {
                    Postulante pp = pos.getPostulante();
                    // Si tenés getId(), mejor comparar por id (evita problemas con equals):
                    try {
                        return pp != null && p != null && pp.getIdPersona() == p.getIdPersona();
                    } catch (Exception e) {
                        // fallback a equals si no hay getId()
                        return pp != null && pp.equals(p);
                    }
                });

        if (yaPostulado) {
            throw new PostulacionDuplicadaException("Ya existe una postulación del candidato para esta vacante");
        }

        Postulacion post = new Postulacion(idPostulacion, p, proceso); // estado inicial: NUEVA
        proceso.agregarPostulacion(post);

        System.out.println("📨 Postulación registrada: " + p.getNombreCompleto());
        return post;
    }

    public void evaluar(Postulacion post, Empleado reclutador, String notas, int puntaje) {
        // Creamos evaluación (usa el id de la postulación para trazar)
        Evaluacion eval = new Evaluacion(post.getIdPostulacion(), notas, puntaje, reclutador);

        // Al asignar evaluación, la postulación pasa a EN_EVALUACION (según tu implementación de Postulacion.asignarEvaluacion)
        post.asignarEvaluacion(eval);

        // Resultado final según puntaje:
        //  - Aprobado  -> SELECCIONADA
        //  - No aprobado -> DESCARTADA
        post.cambiarEstado(eval.esAprobado() ? EstadoPostulacion.SELECCIONADA
                : EstadoPostulacion.DESCARTADA);

        System.out.println("📝 Evaluación cargada: " + eval);
    }
}