package servicios;

import modelo.Clases.Direccion;
import modelo.Clases.ExperienciaLaboral;
import modelo.Clases.Postulante;
import modelo.Clases.Skill;
import modelo.enums.NivelSkill;
import persistencia.IRepositorio;
import persistencia.RepositorioArchivo;
import excepciones.EdadInvalidaException;
import excepciones.EmailInvalidoException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class GestorPostulantes {

    private final List<Postulante> postulantes = new ArrayList<>();
    private final IRepositorio<Postulante> repo;

    public GestorPostulantes() {
        // ⚠️ Ajusta la ruta si usás otra carpeta
        this.repo = new RepositorioArchivo<>(
                "data/postulantes.csv",
                // ---------- Serializer: Postulante -> línea CSV ----------
                p -> String.join(";",
                        Integer.toString(p.getIdPersona()),
                        safe(p.getNombre()),
                        safe(p.getApellido()),
                        safe(p.getEmail()),
                        // si tu clase guarda DNI real, reemplaza por p.getDni()
                        "00000000",
                        // si tenés getter real de fecha de nacimiento: p.getFechaNacimiento().toString()
                        LocalDate.of(1990,1,1).toString(),
                        safe(p.getDireccion().getCalle()),
                        safe(p.getDireccion().getCiudad()),
                        safe(p.getDireccion().getProvincia()),
                        safe(p.getDireccion().getCodigoPostal()),
                        // si guardás la ruta del CV real, reemplaza
                        "cv.pdf",
                        // si tenés p.isActivo(): String.valueOf(p.isActivo())
                        "true",
                        skillsToCsv(p.getSkills()),
                        experienciasToCsv(p.getExperiencias())
                ),
                // ---------- Deserializer: línea CSV -> Postulante ----------
                linea -> {
                    try {
                        String[] a = linea.split(";", -1);
                        if (a.length < 12) return null;
                        int idx = 0;
                        int id = Integer.parseInt(a[idx++]);
                        String nombre = a[idx++];
                        String apellido = a[idx++];
                        String email = a[idx++];
                        String dni = a[idx++]; // si no lo usás, podés ignorarlo
                        LocalDate fechaNac = LocalDate.parse(a[idx++]);
                        String calle = a[idx++];
                        String ciudad = a[idx++];
                        String provincia = a[idx++];
                        String cp = a[idx++];
                        String cv = a[idx++];
                        boolean activo = Boolean.parseBoolean(a[idx++]);

                        String skillsStr = (idx < a.length) ? a[idx++] : "";
                        String expStr = (idx < a.length) ? a[idx] : "";

                        Direccion dir = new Direccion(calle, ciudad, provincia, cp);
                        Postulante p = new Postulante(id, nombre, apellido, email, dni, fechaNac, dir, cv);

                        // aplicar activo si tenés setter (opcional)
                        // if (!activo) p.setActivo(false);

                        parseSkills(skillsStr, p);
                        parseExperiencias(expStr, p);
                        return p;
                    } catch (Exception e) {
                        // Si hay error de formato/validación, omitimos la línea
                        return null;
                    }
                }
        );

        // Precarga desde archivo
        try {
            postulantes.addAll(repo.listar());
        } catch (IOException e) {
            System.err.println("No se pudo cargar postulantes: " + e.getMessage());
        }
    }

    // ====== CASO DE USO: Alta de Candidato ======
    public Postulante altaCandidato(int id,
                                    String nombre,
                                    String apellido,
                                    String email,
                                    String dni,
                                    LocalDate fechaNac,
                                    Direccion dir,
                                    String cv)
            throws EdadInvalidaException, EmailInvalidoException, IOException {

        Postulante p = new Postulante(id, nombre, apellido, email, dni, fechaNac, dir, cv);
        // persistimos
        repo.guardar(p);
        // mantenemos cache en memoria
        postulantes.add(p);
        return p;
    }

    // ====== Búsquedas / Reportes ======
    public List<Postulante> buscarPorSkill(String skillBuscada) {
        String s = skillBuscada == null ? "" : skillBuscada.trim().toLowerCase();
        return postulantes.stream()
                .filter(p -> p.getSkills().stream()
                        .anyMatch(sk -> sk.getNombre().equalsIgnoreCase(s)))
                .collect(Collectors.toList());
    }

    public List<Postulante> getPostulantes() {
        return Collections.unmodifiableList(postulantes);
    }

    // ====== Helpers de CSV ======
    private static String safe(String s) {
        return (s == null) ? "" : s.replace(";", ",");
    }

    private static String skillsToCsv(List<Skill> list) {
        if (list == null || list.isEmpty()) return "";
        return list.stream()
                .map(s -> s.getNombre() + ":" + s.getNivelRequerido())
                .collect(Collectors.joining(","));
    }

    private static String experienciasToCsv(List<ExperienciaLaboral> list) {
        if (list == null || list.isEmpty()) return "";
        // Formato: empresa|anios|puesto,empresa|anios|puesto
        return list.stream()
                .map(e -> safe(e.toString())) // si querés algo más estructurado, usa empresa|anios|puesto
                .collect(Collectors.joining(","));
    }

    private static void parseSkills(String skillsStr, Postulante p) {
        if (skillsStr == null || skillsStr.isEmpty()) return;
        String[] items = skillsStr.split(",");
        for (String it : items) {
            String[] kv = it.split(":");
            if (kv.length == 2) {
                String nombre = kv[0].trim();
                NivelSkill nivel = NivelSkill.valueOf(kv[1].trim());
                p.agregarSkill(nombre, nivel);
            }
        }
    }

    private static void parseExperiencias(String expStr, Postulante p) {
        // Si guardás experiencias en un formato estructurado, parsealas acá y hacé:
        // p.agregarExperiencia(new ExperienciaLaboral(empresa, anios, puesto));
        // En este ejemplo, lo dejamos como texto (porque depende de tu implementación).
    }
}

