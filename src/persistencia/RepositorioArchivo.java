package persistencia;

import java.io.*;
import java.util.*;
import java.util.function.Function;

/**
 * Clase genérica para manejar persistencia de datos en archivos CSV.
 * Implementa el principio SRP (Single Responsibility) y cumple el contrato IRepositorio.
 * @param <T> Tipo de entidad que se guardará en el archivo.
 */
public class RepositorioArchivo<T> implements IRepositorio<T> {
    private final String path; //ruta
    private final Function<T, String> serializer;   // Conversión de objeto -> línea CSV
    private final Function<String, T> deserializer; // Conversión de línea CSV -> objeto

    public RepositorioArchivo(String path, Function<T, String> serializer, Function<String, T> deserializer) {
        this.path = path;
        this.serializer = serializer;
        this.deserializer = deserializer;
    }

    @Override
    public void guardar(T entidad) throws IOException {
        File file = new File(path);
        file.getParentFile().mkdirs(); // Crea carpeta data si no existe

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            bw.write(serializer.apply(entidad));
            bw.newLine();
        }
    }

    @Override
    public List<T> listar() throws IOException {
        List<T> lista = new ArrayList<>();
        File file = new File(path);
        if (!file.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                T entidad = deserializer.apply(linea);
                if (entidad != null) lista.add(entidad);
            }
        }
        return lista;
    }

    @Override
    public Optional<T> buscarPorId(int id) throws IOException {
        return listar().stream()
                .filter(e -> serializer.apply(e).startsWith(id + ";"))
                .findFirst();
    }
}

