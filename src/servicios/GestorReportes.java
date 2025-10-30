package servicios;

import modelo.*;
import modelo.Clases.ProcesoSeleccion;
import modelo.Cliente;
import modelo.enums.*;
import java.util.*;

public class GestorReportes {

    public void reportePorCliente(Cliente cliente) {
        System.out.println("\n📊 Reporte de Procesos de " + cliente.getNombreCompleto());
        for (ProcesoSeleccion p : cliente.getProcesosActivos()) {
            System.out.println("- " + p.getPuesto() + " | Estado: " + p.getEstado());
        }
    }

    public void reportePorEstado(List<ProcesoSeleccion> procesos, EstadoVacante estado) {
        System.out.println("\n📋 Procesos en estado " + estado + ":");
        procesos.stream()
                .filter(p -> p.getEstado() == estado)
                .forEach(p -> System.out.println("• " + p.getPuesto()));
    }
}
