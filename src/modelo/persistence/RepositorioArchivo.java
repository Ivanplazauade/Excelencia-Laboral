package modelo.persistence;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class RepositorioArchivo {

    protected final String nombreArchivo;

    public RepositorioArchivo(String nombreArchivo) {
        this.nombreArchivo = "data/" + nombreArchivo;
        crearDirectorioSiNoExiste();
    }

    private void crearDirectorioSiNoExiste() {
        File dir = new File("data");
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    protected List<String> leerArchivo() {
        List<String> lineas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            System.err.println("Error al leer el archivo " + nombreArchivo + ": " + e.getMessage());
        }
        return lineas;
    }

    protected void escribirArchivo(List<String> lineas) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo))) {
            for (String linea : lineas) {
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo " + nombreArchivo + ": " + e.getMessage());
        }
    }

    protected abstract String serializar(Object entidad);
    protected abstract Object deserializar(String linea);
}