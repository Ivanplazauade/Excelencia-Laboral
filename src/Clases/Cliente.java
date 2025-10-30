package Clases;

import java.util.List;

public class Cliente extends Persona{

    private String razonSocial;
    private String cuit;
    private List<ProcesoSeleccion> procesosActivos;

    //Metodos

    public void solicitarProceso(String puesto, List<Skill> requeridas){};

    public List<ProcesoSeleccion> verProcesosActivos() {
        return null;
    }


    @Override
    public String getNombreCompleto() {
        return "";
    }

    @Override
    public String getTipoPersona() {
        return "";
    }
}
