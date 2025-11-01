# Excelencia Laboral — README (con SOLID & GRASP)

> **Objetivo del proyecto:** Sistema de gestión de procesos de selección (postulantes, clientes, vacantes y evaluaciones) con capa de negocio, persistencia en archivos/DAO y GUI Swing. Este README enfatiza **cómo se aplican SOLID y GRASP** y mapea los entregables a la consigna.

---

## Índice
1. [Arquitectura general](#arquitectura-general)
2. [Dominio y jerarquía OO](#dominio-y-jerarquía-oo)
3. [Principios SOLID aplicados](#principios-solid-aplicados)
4. [Patrones GRASP aplicados](#patrones-grasp-aplicados)
5. [Persistencia y DAO/Repositorio](#persistencia-y-daorepositorio)
6. [Colecciones, ordenamiento y utilidades](#colecciones-ordenamiento-y-utilidades)
7. [Excepciones y manejo de errores](#excepciones-y-manejo-de-errores)
8. [Ejecución por consola (Fase 2)](#ejecución-por-consola-fase-2)
9. [GUI Swing (Fase 3)](#gui-swing-fase-3)
10. [Checklist contra la consigna](#checklist-contra-la-consigna)

---

## Arquitectura general

- **Capa de Presentación (GUI / Consola):**
  - `MainFrame`, `MenuPrincipal`, `VistaPostulante`, `VistaProcesoSeleccion` (Swing)
  - (Opcional para Fase 2) `console/MainConsole` para pruebas por consola.

- **Capa de Aplicación/Servicios (Controladores de Casos de Uso):**
  - `GestorPostulantes`, `GestorProcesos`, `GestorClientes`, etc.
  - Orquestan operaciones, validan y coordinan repositorios.

- **Capa de Dominio (Entidades/Valor/Reglas):**
  - `Persona` (abstracta), `Postulante`, `Cliente`, `Usuario`, `Empleado`.
  - `ProcesoSeleccion`, `Vacante`, `Postulacion`, `Skill`, `ExperienciaLaboral`, `Direccion` (inmutable), `Rol`, `EstadoVacante`, `NivelSkill`, `ResultadoEntrevista` (Enums).

- **Capa de Persistencia (SRP):**
  - Interfaces: `IRepositorio<T>`
  - Implementaciones: `RepositorioArchivo<T>` + `RepositorioPostulante`, `RepositorioProceso`, etc.
  - (Abstracción para DB) `IRepositorio` permite reemplazar almacenamiento sin tocar negocio.

---

## Dominio y jerarquía OO

- **Herencia y Polimorfismo**
  - `Persona` (abstracta) → `Postulante`, `Cliente`, `Usuario`, `Empleado`.
  - Métodos polimórficos sobrescritos (por ejemplo, representación, validaciones específicas).

- **Composición / Agregación / Dependencias**
  - `Postulante` compone/agrega `List<Skill>`, `List<ExperienciaLaboral>`.
  - `ProcesoSeleccion` agrega `Vacante` y `List<Postulacion>`.
  - `Vista*` depende de `Gestor*` (controladores de aplicación).

---

## Principios SOLID aplicados

### S — Single Responsibility (Responsabilidad Única)
- **Evidencia:**
  - Entidades de dominio **no** contienen lógica de E/S ni persistencia.
  - `Repositorio*` se responsabiliza **solo** de leer/escribir.
  - `Gestor*` se responsabiliza de **coordinar casos de uso**.

### O — Open/Closed (Abierto/Cerrado)
- **Evidencia:**
  - `IRepositorio<T>` permite **extender** con nuevas fuentes (CSV, JSON, DB) sin modificar servicios.
  - Nuevas estrategias de ordenamiento con `Comparator` pueden **agregarse** sin tocar entidades.

### L — Liskov Substitution (Sustitución de Liskov)
- **Evidencia:**
  - Cualquier `Persona` puede manejarse por su tipo base sin romper contratos (p.ej., listado de responsables puede incluir `Cliente` o `Empleado`).
  - Métodos del cliente de la jerarquía trabajan con `Persona` sin asumir subclases concretas.

### I — Interface Segregation (Segregación de Interfaces)
- **Evidencia:**
  - `IRepositorio<T>` expone el **contrato mínimo** (p. ej., `guardar`, `obtenerTodos`, `buscarPorId`, `eliminar`).
  - Si se requieren capacidades adicionales, se crean interfaces **acotadas** (por ejemplo, `BuscablePorEmail`).

### D — Dependency Inversion (Inversión de Dependencias)
- **Evidencia:**
  - `Gestor*` depende de **abstracciones** (`IRepositorio<T>`) en lugar de concretos.
  - La inyección del repositorio concreto ocurre en el **composition root** (setup de app/GUI), no dentro del dominio.

---

## Patrones GRASP aplicados

### Controller (Controlador)
- **Quién:** `GestorPostulantes`, `GestorProcesos`, `GestorClientes`.
- **Rol:** Reciben eventos de UI (o consola), validan, coordinan entidades y repositorios, y devuelven resultados/errores.

### Creator (Creador)
- **Quién:** `GestorProcesos` crea `Postulacion`/`ProcesoSeleccion` cuando recibe datos válidos.
- **Justificación:** Posee/usa datos para construirlos y mantener invariantes del flujo.

### Information Expert (Experto en Información)
- **Quién:** Entidades del dominio encapsulan la lógica que depende de sus propios datos (p. ej., `Postulante` conoce su edad, `Vacante` conoce si está abierta/cerrada, `ProcesoSeleccion` calcula avance).

### Low Coupling & High Cohesion (Bajo Acoplamiento / Alta Cohesión)
- **Cómo:** Capas separadas; cada clase tiene una única razón de cambio; dependencias invertidas hacia interfaces. Evita “Dios” y promueve cohesión en `Gestor*`.

### Polymorphism (Polimorfismo)
- **Dónde:** Operaciones que varían según subclase de `Persona` o según estado de `Vacante`/`ProcesoSeleccion` se resuelven por métodos sobrescritos o estrategias.

### Pure Fabrication (Fabricación Pura)
- **Quién:** `RepositorioArchivo<T>` y repos específicos **no** son del dominio, pero existen para **cumplir SRP** y apoyar bajo acoplamiento.

> **Nota:** Estos GRASP justifican la distribución de responsabilidades y la separación de capas.

---

## Persistencia y DAO/Repositorio

- **Contrato de persistencia:** `IRepositorio<T>` define operaciones CRUD mínimas.
- **Implementaciones de archivos:** `RepositorioArchivo<T>` + repos concretos por entidad.
- **Formato:** CSV/TXT (según repositorio). Serialización/parseo simple.
- **Recursos seguros:** **`try-with-resources`** en lectura/escritura para evitar fugas y cerrar flujos automáticamente.
- **Abstracción DB:** una futura implementación (p. ej., JDBC/JPA) puede añadirse sin tocar la capa de negocio.

**Ejemplo (CSV, try-with-resources):**
```java
try (BufferedReader br = Files.newBufferedReader(ruta, StandardCharsets.UTF_8)) {
    String linea;
    while ((linea = br.readLine()) != null) {
        // parsear y mapear a entidad
    }
}
```

---

## Colecciones, ordenamiento y utilidades

- **Colecciones genéricas:** `List<Postulante>`, `Map<String, Cliente>`, `Set<Skill>`.
- **Ordenamiento:**
  - Opción A: `Postulante implements Comparable<Postulante>` (por `apellido, nombre`).
  - Opción B: `Comparator<Postulante>` en `GestorReportes` (por `puntaje`, `fechaAlta`, etc.).
- **Enums:** `Rol`, `EstadoVacante`, `NivelSkill`, `ResultadoEntrevista`.
- **Objeto inmutable:** `Direccion` con campos `final` y sin setters.
- **`static`:** constantes, fábricas o utilitarios donde tenga sentido.

**Ejemplo (Comparator):**
```java
Comparator<Postulante> porApellidoNombre = Comparator
        .comparing(Postulante::getApellido)
        .thenComparing(Postulante::getNombre);
lista.sort(porApellidoNombre);
```

---

## Excepciones y manejo de errores

- **Excepciones de negocio (checked):** `EdadInvalidaException`, `EmailInvalidoException`, `VacanteCerradaException`, `PostulacionDuplicadaException`, etc.
- **Validaciones típicas:**
  - `validarEdad(LocalDate fecha)` lanza `EdadInvalidaException` si `< 18`.
  - Estados de `Vacante`/`ProcesoSeleccion`.
- **Errores comunes controlados:** `NumberFormatException`, `NullPointerException` con `try-catch` y mensajes al usuario.
- **GUI:** Validaciones antes de persistir; feedback con `JOptionPane`/actualización de modelos de tabla.

---

## Ejecución por consola (Fase 2)

> **Objetivo docente:** demostrar Core sin GUI.

- **Clase sugerida:** `console/MainConsole` con menú básico (alta/listado de postulantes, crear proceso, postular, guardar/cargar).
- **Uso:**
  ```bash
  # desde el root del proyecto
  javac -d out $(find src -name "*.java")
  java -cp out console.MainConsole
  ```

---

## GUI Swing (Fase 3)

- **Pantallas:** `MainFrame` (principal), `VistaPostulante` y `VistaProcesoSeleccion` (formularios).
- **Eventos:** `addActionListener` en botones → llama a `Gestor*` → actualiza `JTable`/`JList` (modelo).
- **Layout/Componentes:** uso de `JPanel`, `JTable`, `JScrollPane`, `JTextField`, `JComboBox`, `JButton` con layouts adecuados.

**Ejemplo (refresco de JTable):**
```java
DefaultTableModel model = (DefaultTableModel) tabla.getModel();
model.setRowCount(0);
for (Postulante p : gestor.obtenerTodos()) {
    model.addRow(new Object[]{p.getDni(), p.getApellido(), p.getNombre()});
}
```

---

## Checklist contra la consigna

### A. Modelado y OO
- [x] **Herencia y Polimorfismo:** `Persona` + subclases con métodos sobrescritos.
- [x] **Interface (contrato):** `IRepositorio<T>` (y otras según necesidad).
- [x] **Composición/Agregación/Dependencia:** relaciones en entidades; UI → Gestores.
- [x] **SOLID + GRASP:** ver secciones dedicadas.

### B. Java Avanzado y Estructura
- [x] **Colecciones genéricas:** List/Map/Set.
- [ ] **Ordenamiento:** `Comparable` **o** `Comparator` aplicado a una colección en un caso de uso.
- [x] **Inmutable/Enum/static:** `Direccion` inmutable; enums listados.
- [x] **Excepciones de negocio (checked).**
- [x] **Gestión de errores + try-with-resources** en persistencia.

### C. Persistencia y Arquitectura
- [x] **Persistencia en Archivos** (TXT/CSV) con flujos + `try-with-resources`.
- [x] **Separación SRP:** paquete de persistencia/DAO sin E/S en dominio.
- [x] **Abstracción DB:** `IRepositorio<T>` lista para implementación futura.

### D. GUI y Eventos
- [x] **GUI funcional:** 1 pantalla principal + 2 formularios.
- [x] **Eventos → Backend → Actualización de componentes** (JTable/Lista).
- [x] **Componentes y Layouts** adecuados.

---


