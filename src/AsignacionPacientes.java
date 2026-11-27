import java.util.ArrayList;
import java.util.HashMap;

public class AsignacionPacientes{

    private FilaEspera filaEspera;
    private EmployeeManager empleados;
    private HashMap<Integer,String> asignaciones;

    public AsignacionPacientes(FilaEspera filaEspera, EmployeeManager empleados){
        this.filaEspera = filaEspera;
        this.empleados= empleados;
        asignaciones = new HashMap<Integer, String>();
    }

public boolean asignarPaciente(){

    // verifica si hay pacientes en la fila de espera
    if (filaEspera.estaVacia()){
        System.out.println("No hay pacientes en espera.");
        return false;

    }

    Paciente paciente= filaEspera.verSiguientePaciente();

    ArrayList<Doctor> medicos = empleados.getAvailableDoctors();

    if (medicos.isEmpty()){
        System.out.println("Medicos no disponibles.");
        return false;
    }


    Doctor medico = medicos.get(0);


    asignaciones.put(paciente.getId(), medico.getId());

    filaEspera.atenderPaciente();

    empleados.setDoctorAvailability(medico.getId(), false);


    System.out.println("Paciente " + paciente.getId() + " asignado al medico " + medico.getId());
    return true;
}
}