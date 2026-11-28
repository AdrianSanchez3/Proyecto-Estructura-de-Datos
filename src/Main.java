import java.time.LocalDate;
import java.util.Scanner;

/**
 * Menu de consola para demostrar el sistema de consultas medicas.
 * Datos en memoria: al salir del programa se pierden los registros.
 */
public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final ManejoPacientes PACIENTES = new ManejoPacientes();
    private static final EmployeeManager EMPLEADOS = new EmployeeManager();
    private static final ManejoCitas CITAS = new ManejoCitas();
    private static final FilaEspera FILA = new FilaEspera(PACIENTES);
    private static final AsignacionPacientes ASIGNACIONES =
            new AsignacionPacientes(FILA, EMPLEADOS);

    public static void main(String[] args) {
        System.out.println("SISTEMA DE CONSULTAS MEDICAS - UPRM");
        int opcion;
        do {
            mostrarMenu();
            opcion = leerInt("Opcion: ");
            try {
                ejecutar(opcion);
            } catch (RuntimeException e) {
                System.out.println("No se pudo completar la operacion: " + e.getMessage());
            }
        } while (opcion != 0);
        System.out.println("Programa finalizado.");
    }

    private static void mostrarMenu() {
        System.out.println("\n============= MENU =============");
        System.out.println(" 1. Cargar datos de demostracion");
        System.out.println(" 2. Registrar paciente");
        System.out.println(" 3. Registrar medico");
        System.out.println(" 4. Registrar enfermera");
        System.out.println(" 5. Registrar secretaria");
        System.out.println(" 6. Buscar paciente por ID");
        System.out.println(" 7. Buscar empleado por ID");
        System.out.println(" 8. Mostrar pacientes registrados");
        System.out.println(" 9. Buscar medicos por especialidad");
        System.out.println("10. Mostrar medicos disponibles");
        System.out.println("11. Anadir paciente a la fila");
        System.out.println("12. Mostrar fila de espera");
        System.out.println("13. Asignar siguiente paciente a un medico");
        System.out.println("14. Consultar medico asignado a paciente");
        System.out.println("15. Finalizar atencion / liberar medico");
        System.out.println("16. Crear cita");
        System.out.println("17. Cancelar cita");
        System.out.println("18. Completar cita / guardar en historial");
        System.out.println("19. Mostrar todas las citas");
        System.out.println("20. Mostrar historial de paciente");
        System.out.println("21. Mostrar citas de hoy");
        System.out.println(" 0. Salir");
        System.out.println("================================");
    }

    private static void ejecutar(int opcion) {
        switch (opcion) {
            case 1:
                cargarDemo();
                break;
            case 2:
                registrarPaciente();
                break;
            case 3:
                registrarMedico();
                break;
            case 4:
                resultado(EMPLEADOS.addNurse(new Nurse(
                        leerTexto("ID enfermera (ej. N001): "), leerTexto("Nombre: "))), "Enfermera registrada");
                break;
            case 5:
                resultado(EMPLEADOS.addSecretary(new Secretary(
                        leerTexto("ID secretaria (ej. S001): "), leerTexto("Nombre: "))), "Secretaria registrada");
                break;
            case 6:
                int idP = leerInt("ID paciente: ");
                Paciente paciente = PACIENTES.buscarPaciente(idP);
                System.out.println(paciente == null ? "Paciente no encontrado." : paciente);
                break;
            case 7:
                String idE = leerTexto("ID empleado: ");
                Object empleado = EMPLEADOS.findEmployeeById(idE);
                System.out.println(empleado == null ? "Empleado no encontrado." : empleado);
                break;
            case 8:
                PACIENTES.mostrarPacientes();
                break;
            case 9:
                buscarPorEspecialidad();
                break;
            case 10:
                mostrarDisponibles();
                break;
            case 11:
                int idFila = leerInt("ID paciente: ");
                FILA.agregarPacienteOExcepcion(idFila);
                System.out.println("Paciente anadido a la fila.");
                break;
            case 12:
                FILA.mostrarPacientesEnEspera();
                System.out.println("Total en espera: " + FILA.getCantidadPacientes());
                break;
            case 13:
                ASIGNACIONES.asignarPacienteOExcepcion();
                break;
            case 14:
                int idCons = leerInt("ID paciente: ");
                String asignado = ASIGNACIONES.obtenerMedicoAsignado(idCons);
                System.out.println(asignado == null ? "Sin asignacion activa." : "Medico asignado: " + asignado);
                break;
            case 15:
                int idFin = leerInt("ID paciente: ");
                ASIGNACIONES.finalizarAtencionOExcepcion(idFin);
                break;
            case 16:
                crearCita();
                break;
            case 17:
                resultado(CITAS.cancelarCita(leerInt("ID cita: ")), "Cita cancelada");
                break;
            case 18:
                resultado(CITAS.completarCita(leerInt("ID cita: "), PACIENTES),
                        "Cita completada y guardada en historial");
                break;
            case 19:
                CITAS.mostrarCitas();
                break;
            case 20:
                PACIENTES.mostrarHistorialClinico(leerInt("ID paciente: "));
                break;
            case 21:
                CITAS.mostrarCitasDeFecha(LocalDate.now().toString());
                break;
            case 0:
                break;
            default:
                System.out.println("Opcion no valida.");
        }
    }

    private static void registrarPaciente() {
        int id = leerInt("ID paciente (numero): ");
        String nombre = leerTexto("Nombre: ");
        int edad = leerInt("Edad: ");
        String especialidad = elegirEspecialidad();
        resultado(PACIENTES.registrarPaciente(new Paciente(id, nombre, edad, especialidad)),
                "Paciente registrado");
    }

    private static void registrarMedico() {
        String id = leerTexto("ID medico (ej. D001): ");
        String nombre = leerTexto("Nombre: ");
        String especialidad = elegirEspecialidad();
        resultado(EMPLEADOS.addDoctor(new Doctor(id, nombre, especialidad)),
                "Medico registrado");
    }

    private static void buscarPorEspecialidad() {
        String especialidad = elegirEspecialidad();
        var medicos = EMPLEADOS.findDoctorsBySpecialty(especialidad);
        if (medicos.isEmpty()) {
            System.out.println("No hay medicos de esa especialidad.");
        } else {
            for (Doctor doctor : medicos) {
                System.out.println(doctor + "\n--------------------");
            }
        }
    }

    private static void mostrarDisponibles() {
        var medicos = EMPLEADOS.getAvailableDoctors();
        if (medicos.isEmpty()) {
            System.out.println("No hay medicos disponibles.");
        } else {
            for (Doctor doctor : medicos) {
                System.out.println(doctor.getId() + " - " + doctor.getName()
                        + " (" + doctor.getSpecialty() + ")");
            }
        }
    }

    private static void crearCita() {
        int id = leerInt("ID cita: ");
        int idPaciente = leerInt("ID paciente: ");
        String idMedico = leerTexto("ID medico (ej. D001): ");
        Paciente paciente = PACIENTES.buscarPaciente(idPaciente);
        Doctor doctor = EMPLEADOS.findDoctorById(idMedico);
        if (paciente == null || doctor == null) {
            System.out.println("El paciente o el medico no esta registrado.");
            return;
        }
        if (!paciente.getEspecialidad().equalsIgnoreCase(doctor.getSpecialty())) {
            System.out.println("La especialidad del medico no coincide con la requerida por el paciente.");
            return;
        }
        String fecha = leerTexto("Fecha YYYY-MM-DD (hoy: " + LocalDate.now() + "): ");
        String hora = leerTexto("Hora HH:MM (24h): ");
        String motivo = leerTexto("Motivo: ");
        CITAS.crearCitaOExcepcion(new Cita(id, idPaciente, idMedico, fecha, hora, motivo));
        System.out.println("Cita creada correctamente.");
    }

    /** Precarga ejemplos, sin crear duplicados al ejecutarse varias veces. */
    private static void cargarDemo() {
        EMPLEADOS.addDoctor(new Doctor("D001", "Carlos Rivera", "General"));
        EMPLEADOS.addDoctor(new Doctor("D002", "Maria Lopez", "Pediatric"));
        EMPLEADOS.addDoctor(new Doctor("D003", "Jose Perez", "Retina"));
        EMPLEADOS.addDoctor(new Doctor("D004", "Ana Torres", "Neuro-Ophthalmology"));
        EMPLEADOS.addNurse(new Nurse("N001", "Laura Diaz"));
        EMPLEADOS.addSecretary(new Secretary("S001", "Pedro Garcia"));
        PACIENTES.registrarPaciente(new Paciente(101, "Lucia", 25, "General"));
        PACIENTES.registrarPaciente(new Paciente(102, "Ana", 15, "Pediatric"));
        PACIENTES.registrarPaciente(new Paciente(103, "Jose", 46, "Retina"));
        System.out.println("Datos de demostracion listos: medicos D001-D004 y pacientes 101-103.");
    }

    private static String elegirEspecialidad() {
        while (true) {
            System.out.println("1. General | 2. Pediatric | 3. Retina | 4. Neuro-Ophthalmology");
            int numero = leerInt("Especialidad: ");
            switch (numero) {
                case 1: return "General";
                case 2: return "Pediatric";
                case 3: return "Retina";
                case 4: return "Neuro-Ophthalmology";
                default: System.out.println("Selecciona una especialidad valida.");
            }
        }
    }

    private static String leerTexto(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            if (!SCANNER.hasNextLine()) {
                System.out.println("\nFin de entrada. Programa finalizado.");
                System.exit(0);
            }
            String texto = SCANNER.nextLine().trim();
            if (!texto.isEmpty()) return texto;
            System.out.println("Este campo no puede estar vacio.");
        }
    }

    private static int leerInt(String mensaje) {
        while (true) {
            String texto = leerTexto(mensaje);
            try {
                return Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                System.out.println("Debes escribir un numero entero.");
            }
        }
    }

    private static void resultado(boolean ok, String exito) {
        System.out.println(ok ? exito + "." : "No se pudo completar: verifica los datos o duplicados.");
    }
}
