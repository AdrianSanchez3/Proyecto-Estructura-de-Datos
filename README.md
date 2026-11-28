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

## Limitaciones pendientes para integracion completa

- `Doctor` usa `String` como ID (`D001`), pero `Cita` y `Visita` usan `int` para ID del medico. Los responsables deben acordar un tipo compartido para conectar citas a empleados.
- `Visita.java` aun no registra automaticamente una atencion clinica; `ManejoPacientes` almacena citas en el historial, no datos clinicos completos.
- `AsignacionPacientes` elige el primer medico disponible: aun no filtra por especialidad ni horario de cita.
- El sistema no tiene persistencia: reiniciar Java pierde los datos registrados.
- Estas clases no contienen un menu general; las pruebas incluyen ejemplos de uso.

## Colaboradores

Proyecto realizado por los integrantes del equipo. Consultar el historial Git del repositorio para autoria de commits.
