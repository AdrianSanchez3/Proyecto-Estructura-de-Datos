/** Pruebas sin JUnit para poder ejecutarlas con javac y java. */
public class TestExcepciones {
    private static int pruebas = 0;

    private static void comprobar(boolean condicion, String descripcion) {
        if (!condicion) {
            throw new AssertionError("FALLO: " + descripcion);
        }
        pruebas++;
        System.out.println("OK: " + descripcion);
    }

    private static void esperarExcepcion(Class<? extends Throwable> clase,
                                         Runnable accion, String descripcion) {
        try {
            accion.run();
            throw new AssertionError("FALLO: se esperaba " + clase.getSimpleName() + ": " + descripcion);
        } catch (Throwable e) {
            if (!clase.isInstance(e)) {
                throw new AssertionError("Excepcion incorrecta en " + descripcion, e);
            }
            pruebas++;
            System.out.println("OK: " + clase.getSimpleName() + " - " + descripcion);
        }
    }

    public static void main(String[] args) {
        ManejoPacientes pacientes = new ManejoPacientes();
        FilaEspera fila = new FilaEspera(pacientes);
        EmployeeManager empleados = new EmployeeManager();
        AsignacionPacientes asignacion = new AsignacionPacientes(fila, empleados);

        esperarExcepcion(FilaVaciaException.class, asignacion::asignarPacienteOExcepcion,
                "No asignar desde una fila vacia");
        comprobar(!asignacion.asignarPaciente(), "API booleana conserva false si fila vacia");
        esperarExcepcion(PacienteNoEncontradoException.class,
                () -> fila.agregarPacienteOExcepcion(999), "Paciente inexistente");
        comprobar(!fila.agregarPaciente(999), "API booleana conserva false si no existe paciente");

        Paciente p1 = new Paciente(101, "Lucia", 25);
        Paciente p2 = new Paciente(102, "Ana", 30);
        comprobar(pacientes.registrarPaciente(p1), "Registrar paciente 101");
        comprobar(pacientes.registrarPaciente(p2), "Registrar paciente 102");
        comprobar(fila.agregarPaciente(101), "Poner paciente 101 en espera");
        esperarExcepcion(AsignacionInvalidaException.class,
                () -> fila.agregarPacienteOExcepcion(101), "Paciente duplicado en fila");
        esperarExcepcion(MedicoNoDisponibleException.class, asignacion::asignarPacienteOExcepcion,
                "Sin medico disponible");
        comprobar(fila.verSiguientePaciente().getId() == 101,
                "Sin medico, el paciente permanece en la fila");

        Doctor doctor = new Doctor("D001", "Carlos", "General");
        comprobar(empleados.addDoctor(doctor), "Registrar medico");
        comprobar(asignacion.asignarPacienteOExcepcion(), "Asignar paciente a medico disponible");
        comprobar("D001".equals(asignacion.obtenerMedicoAsignado(101)),
                "Consultar medico asignado");
        comprobar(!empleados.isDoctorAvailable("D001"), "Medico queda ocupado");
        comprobar(fila.estaVacia(), "Paciente sale de la fila al ser asignado");

        comprobar(fila.agregarPaciente(101), "Paciente puede regresar a la fila");
        esperarExcepcion(AsignacionInvalidaException.class, asignacion::asignarPacienteOExcepcion,
                "No duplicar una asignacion activa");
        comprobar(fila.getCantidadPacientes() == 1, "No se pierde al paciente ante error");
        comprobar(asignacion.finalizarAtencionOExcepcion(101), "Finalizar atencion");
        comprobar(empleados.isDoctorAvailable("D001"), "Liberar al medico");
        comprobar(asignacion.obtenerMedicoAsignado(101) == null, "Eliminar asignacion activa");
        esperarExcepcion(AsignacionInvalidaException.class,
                () -> asignacion.finalizarAtencionOExcepcion(101), "No finalizar dos veces");
        comprobar(asignacion.asignarPaciente(), "Se puede reasignar despues de finalizar");
        comprobar(asignacion.finalizarAtencion(101), "API booleana finaliza correctamente");
        comprobar(!asignacion.finalizarAtencion(101), "API booleana rechaza finalizar dos veces");

        ManejoCitas citas = new ManejoCitas();
        Cita c1 = new Cita(1, 101, 1, "2026-11-30", "10:00", "Consulta");
        comprobar(citas.crearCita(c1), "Crear primera cita");
        esperarExcepcion(CitaConflictivaException.class,
                () -> citas.crearCitaOExcepcion(new Cita(1, 102, 2, "2026-11-30", "11:00", "Duplicado")),
                "No permitir ID de cita duplicado");
        esperarExcepcion(CitaConflictivaException.class,
                () -> citas.crearCitaOExcepcion(new Cita(2, 102, 1, "2026-11-30", "10:00", "Choque medico")),
                "No permitir dos citas del mismo medico a la misma hora");
        esperarExcepcion(CitaConflictivaException.class,
                () -> citas.crearCitaOExcepcion(new Cita(3, 101, 2, "2026-11-30", "10:00", "Choque paciente")),
                "No permitir dos citas del mismo paciente a la misma hora");
        comprobar(!citas.crearCita(new Cita(4, 102, 1, "2026-11-30", "10:00", "Choque")),
                "API booleana rechaza double booking");
        comprobar(citas.cancelarCita(1), "Cancelar cita libera horario");
        comprobar(citas.crearCita(new Cita(4, 102, 1, "2026-11-30", "10:00", "Reagendada")),
                "Se puede reservar el horario tras cancelar");
        esperarExcepcion(IllegalArgumentException.class,
                () -> citas.crearCitaOExcepcion(null), "No aceptar cita nula");
        System.out.println("TODAS LAS " + pruebas + " PRUEBAS DE EXCEPCIONES PASARON");
    }
}
