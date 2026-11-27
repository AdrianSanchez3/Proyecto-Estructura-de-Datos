public class TestIntegracionPacientesEmpleados {
    private static void verificar(boolean condicion, String mensaje) {
        if (!condicion) throw new AssertionError(mensaje);
        System.out.println("OK: " + mensaje);
    }

    public static void main(String[] args) {
        ManejoPacientes pacientes = new ManejoPacientes();
        EmployeeManager empleados = new EmployeeManager();
        FilaEspera fila = new FilaEspera(pacientes);

        Paciente p = new Paciente(101, "Lucia", 25);
        verificar(pacientes.registrarPaciente(p), "Registrar paciente");
        verificar(!pacientes.registrarPaciente(new Paciente(101, "Duplicado", 20)), "Rechazar ID duplicado");
        verificar(!pacientes.registrarPaciente(new Paciente(-1, "Invalido", 20)), "Rechazar ID invalido");
        verificar(fila.agregarPaciente(101), "Anadir paciente a la fila");
        verificar(!fila.agregarPaciente(101), "Evitar duplicado en fila");
        verificar(fila.verSiguientePaciente().getId() == 101, "Consultar primero en fila");

        Doctor medico = new Doctor("D001", "Carlos Rivera", "General");
        verificar(empleados.addDoctor(medico), "Registrar medico");
        verificar(empleados.isDoctorAvailable("D001"), "Medico disponible");

        Paciente siguiente = fila.atenderPaciente();
        verificar(siguiente != null && siguiente.getId() == 101, "Atender siguiente paciente");
        verificar(empleados.setDoctorAvailability("D001", false), "Ocupar medico");
        verificar(!empleados.isDoctorAvailable("D001"), "Medico deja de estar disponible");
        verificar(fila.estaVacia(), "Fila vacia tras atencion");
        verificar(empleados.setDoctorAvailability("D001", true), "Liberar medico");

        // Cita se usa aqui solo como registro de historial; no es el modulo de Sergio.
        Cita registro = new Cita(1, 101, 1, "2026-11-27", "10:00", "Consulta general");
        verificar(pacientes.registrarVisita(registro), "Guardar cita en historial del paciente");
        verificar(!pacientes.registrarVisita(registro), "Evitar cita duplicada en historial");
        verificar(!pacientes.registrarVisita(new Cita(2, 999, 1, "2026-11-27", "11:00", "Prueba")), "Rechazar paciente inexistente");
        System.out.println("TODAS LAS PRUEBAS PASARON");
    }
}
