import java.util.HashMap;

public class ManejoPacientes {

    private HashMap<Integer, Paciente> pacientes;

    public ManejoPacientes() {
        pacientes = new HashMap<Integer, Paciente>();
    }

    // Registra un nuevo paciente.
    public boolean registrarPaciente(Paciente nuevoPaciente) {

        if (nuevoPaciente == null) {
            return false;
        }

        if (nuevoPaciente.getId() <= 0
                || nuevoPaciente.getEdad() < 0
                || nuevoPaciente.getNombre() == null
                || nuevoPaciente.getNombre().trim().isEmpty()) {
            return false;
        }

        // Verifica que el ID no exista.
        if (pacientes.containsKey(nuevoPaciente.getId())) {
            return false;
        }

        pacientes.put(nuevoPaciente.getId(), nuevoPaciente);
        return true;
    }

    // Busca un paciente por su ID.
    public Paciente buscarPaciente(int id) {
        return pacientes.get(id);
    }

    // Variante estricta: lanza una excepcion si el ID no esta registrado.
    public Paciente buscarPacienteOExcepcion(int id) {
        Paciente paciente = buscarPaciente(id);
        if (paciente == null) {
            throw new PacienteNoEncontradoException("No existe un paciente con ID " + id);
        }
        return paciente;
    }

    // Verifica si el paciente esta registrado.
    public boolean existePaciente(int id) {
        return pacientes.containsKey(id);
    }

    // Registra una visita en el historial del paciente.
    public boolean registrarVisita(Cita cita) {

        if (cita == null) {
            return false;
        }

        Paciente paciente = buscarPaciente(cita.getIdPaciente());

        if (paciente == null) {
            return false;
        }

        return paciente.agregarCita(cita);
    }

    // Muestra el historial de un paciente especifico.
    public void mostrarHistorialClinico(int id) {

        Paciente paciente = buscarPaciente(id);

        if (paciente == null) {
            System.out.println("Paciente no encontrado.");
            return;
        }

        paciente.mostrarHistorialClinico();
    }

    // Muestra todos los pacientes registrados.
    public void mostrarPacientes() {

        if (pacientes.isEmpty()) {
            System.out.println("No hay pacientes registrados.");
            return;
        }

        for (Paciente paciente : pacientes.values()) {
            System.out.println(paciente);
            System.out.println("--------------------");
        }
    }

    public int getCantidadPacientes() {
        return pacientes.size();
    }
}