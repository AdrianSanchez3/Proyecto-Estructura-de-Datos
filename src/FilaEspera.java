import java.util.Queue;
import java.util.LinkedList;
import java.util.HashSet;

public class FilaEspera {
    private final Queue<Paciente> filaEspera;
    private final HashSet<Integer> pacientesEnEspera;
    private final ManejoPacientes manejoPacientes;

    public FilaEspera(ManejoPacientes manejoPacientes) {
        if (manejoPacientes == null) {
            throw new IllegalArgumentException("ManejoPacientes es obligatorio.");
        }
        this.manejoPacientes = manejoPacientes;
        filaEspera = new LinkedList<>();
        pacientesEnEspera = new HashSet<>();
    }

    // API original para mantener compatibilidad con las pruebas existentes.
    public boolean agregarPaciente(int id) {
        try {
            return agregarPacienteOExcepcion(id);
        } catch (PacienteNoEncontradoException | AsignacionInvalidaException e) {
            return false;
        }
    }

    public boolean agregarPacienteOExcepcion(int id) {
        Paciente paciente = manejoPacientes.buscarPacienteOExcepcion(id);
        if (pacientesEnEspera.contains(id)) {
            throw new AsignacionInvalidaException("El paciente " + id + " ya esta en espera.");
        }
        filaEspera.offer(paciente);
        pacientesEnEspera.add(id);
        return true;
    }

    public Paciente verSiguientePaciente() {
        return filaEspera.peek();
    }

    public Paciente atenderPaciente() {
        if (filaEspera.isEmpty()) {
            return null;
        }
        Paciente paciente = filaEspera.poll();
        pacientesEnEspera.remove(paciente.getId());
        return paciente;
    }

    public boolean estaEnEspera(int id) {
        return pacientesEnEspera.contains(id);
    }

    public void mostrarPacientesEnEspera() {
        if (filaEspera.isEmpty()) {
            System.out.println("No hay pacientes en espera.");
            return;
        }
        System.out.println("PACIENTES EN ESPERA");
        for (Paciente paciente : filaEspera) {
            System.out.println(paciente);
            System.out.println("--------------------");
        }
    }

    public boolean estaVacia() {
        return filaEspera.isEmpty();
    }

    public int getCantidadPacientes() {
        return filaEspera.size();
    }
}
