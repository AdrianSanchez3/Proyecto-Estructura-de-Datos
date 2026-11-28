# Sistema de Manejo de Consultas Medicas

Proyecto de Estructuras de Datos (Java), sistema sencillo para pacientes, personal, fila FIFO, citas y asignaciones.

## Estructuras de datos

- `EmployeeManager`: `HashMap<String,Object>` para buscar empleados por ID, `ArrayList<Doctor>` para listar medicos y `HashSet<String>` para registrar IDs de medicos.
- `ManejoPacientes`: `HashMap<Integer,Paciente>` para registros de pacientes.
- `FilaEspera`: `Queue<Paciente>` con `LinkedList` (FIFO) y `HashSet<Integer>` para evitar pacientes repetidos en la fila.
- `ManejoCitas`: `ArrayList<Cita>`; verifica IDs de cita y conflictos de fecha/hora por medico y paciente.
- `AsignacionPacientes`: `HashMap<Integer,String>` con asignaciones activas paciente -> ID del medico.

## Instalacion

Instala JDK 21+ y abre una terminal en la carpeta principal del proyecto.

```powershell
javac src/*.java
```

## Ejecutar el sistema (terminal)

```powershell
javac src/*.java
java -cp src Main
```

El menu de `Main.java` permite registrar pacientes y personal, consultar medicos, gestionar
la fila, asignar y liberar medicos, crear/cancelar/completar citas y ver historiales.
La opcion `1` carga datos de demostracion (medicos D001-D004 y pacientes 101-103).
Ejemplo: opcion `1`, luego `11` con paciente `101`, luego `13` para asignar.
Para registrar una cita, usa la opcion `16`; al completarla con `18` se guarda
en el historial, que puedes ver con `20`. La opcion `21` muestra las citas de hoy.

Los registros estan en memoria y se pierden al cerrar el programa.

## Ejecutar pruebas

```powershell
java -cp src TestEmployees
java -cp src TestIntegracionPacientesEmpleados
java -cp src TestExcepciones
```

## Manejo de errores (exceptions)

Se conservan los metodos originales que devuelven `boolean`, para no romper el codigo de otros integrantes.
Adicionalmente, los metodos con el sufijo `OExcepcion` lanzan excepciones de negocio, capturables con `try/catch`:

- `FilaVaciaException`: se solicita una asignacion sin pacientes en espera.
- `PacienteNoEncontradoException`: se intenta encolar un paciente no registrado.
- `MedicoNoDisponibleException`: no hay medico disponible para asignar o liberar.
- `AsignacionInvalidaException`: paciente ya asignado, repetido en fila o asignacion no existente.
- `CitaConflictivaException`: se repite un ID o se intenta reservar un horario ya ocupado.

Ejemplo:

```java
try {
    asignaciones.asignarPacienteOExcepcion();
} catch (FilaVaciaException | MedicoNoDisponibleException | AsignacionInvalidaException e) {
    System.out.println("No se pudo asignar: " + e.getMessage());
}
```

`asignarPaciente()` sigue devolviendo `true` o `false`. `finalizarAtencion(idPaciente)` libera al medico y elimina la asignacion activa. `crearCita()` mantiene el resultado booleano.

## Limitaciones conocidas

- Los IDs de medicos son `String` (por ejemplo `D001`) en `Doctor`, `Cita` y `Visita`.
- El historial clinico almacena citas; `Visita.java` no guarda diagnosticos u observaciones.
- Las asignaciones filtran por especialidad y disponibilidad general, pero no reservan horarios especificos.
- No hay persistencia: al reiniciar Java se pierden los registros.

## Colaboradores

Proyecto realizado por los integrantes del equipo. Consultar el historial Git del repositorio para autoria de commits.
