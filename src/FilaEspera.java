import java.util.Queue;
import java.util.LinkedList;
import java.util.HashSet;

public class FilaEspera {

    private Queue<Paciente> filaEspera;
    private HashSet<Integer> pacientesEnEspera;
    private ManejoPacientes manejoPacientes;

    public FilaEspera(ManejoPacientes manejoPacientes) {

        this.manejoPacientes = manejoPacientes;

        filaEspera = new LinkedList<Paciente>();
        pacientesEnEspera = new HashSet<Integer>();
    }

    // anade un paciente a la fila 
    public boolean agregarPaciente(int id) {

        Paciente paciente = manejoPacientes.buscarPaciente(id);

        if (paciente == null) {
            return false;
        }

        // chequiar si el paciente esta en fila de espera 
        if (pacientesEnEspera.contains(id)) {
            return false;
        }

        filaEspera.offer(paciente);
        pacientesEnEspera.add(id);

        return true;
    }

    // ver el paciente sin eliminarlo de fila 
    public Paciente verSiguientePaciente() {

        return filaEspera.peek();
    }

    // paciente atendido-> sacar de fila 
    public Paciente atenderPaciente() {

        if (filaEspera.isEmpty()) {
            return null;
        }

        Paciente paciente = filaEspera.poll();

        pacientesEnEspera.remove(paciente.getId());

        return paciente;
    }

    // verifica quien esta en espera 
    public boolean estaEnEspera(int id) {

        return pacientesEnEspera.contains(id);
    }

    // ensena todos lso pacientes en fila de espera 
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

    // verifica si la fila esta vacia 
    public boolean estaVacia() {

        return filaEspera.isEmpty();
    }

    // cantidad de pacientes esperando 
    public int getCantidadPacientes() {

        return filaEspera.size();
    }
}