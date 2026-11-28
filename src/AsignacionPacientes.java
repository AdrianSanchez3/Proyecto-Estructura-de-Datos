import java.util.ArrayList;
import java.util.HashMap;

/** Administra asignaciones activas entre pacientes en espera y medicos. */
public class AsignacionPacientes {
    private final FilaEspera filaEspera;
    private final EmployeeManager empleados;
    private final HashMap<Integer, String> asignaciones;

    public AsignacionPacientes(FilaEspera filaEspera, EmployeeManager empleados) {
        if (filaEspera == null || empleados == null) {
            throw new IllegalArgumentException("La fila y el gestor de empleados son obligatorios.");
        }
        this.filaEspera = filaEspera;
        this.empleados = empleados;
        this.asignaciones = new HashMap<>();
    }

    // API existente: conserva true/false para no romper a quienes ya la utilizan.
    public boolean asignarPaciente() {
        try {
            return asignarPacienteOExcepcion();
        } catch (FilaVaciaException | MedicoNoDisponibleException | AsignacionInvalidaException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // API estricta: permite al programa principal manejar situaciones con try/catch.
    public boolean asignarPacienteOExcepcion() {
        Paciente paciente = filaEspera.verSiguientePaciente();
        if (paciente == null) {
            throw new FilaVaciaException("No hay pacientes en espera.");
        }
        if (asignaciones.containsKey(paciente.getId())) {
            throw new AsignacionInvalidaException(
                "El paciente " + paciente.getId() + " ya tiene un medico asignado.");
        }

        ArrayList<Doctor> medicosDisponibles = empleados.getAvailableDoctors();
        if (medicosDisponibles.isEmpty()) {
            throw new MedicoNoDisponibleException("No hay medicos disponibles.");
        }

        Doctor medico = medicosDisponibles.get(0);
        // Solo retirar al paciente cuando sabemos que podemos ocupar al medico.
        if (!empleados.setDoctorAvailability(medico.getId(), false)) {
            throw new MedicoNoDisponibleException("No se pudo reservar el medico " + medico.getId());
        }
        Paciente atendido = filaEspera.atenderPaciente();
        if (atendido == null) {
            // Seguridad: no dejamos al medico ocupado si no se pudo retirar a nadie.
            empleados.setDoctorAvailability(medico.getId(), true);
            throw new FilaVaciaException("La fila quedo vacia antes de asignar al paciente.");
        }
        asignaciones.put(atendido.getId(), medico.getId());
        System.out.println("Paciente " + atendido.getId() + " asignado al medico " + medico.getId());
        return true;
    }

    /** Devuelve el ID del medico asignado, o null si no hay asignacion activa. */
    public String obtenerMedicoAsignado(int idPaciente) {
        return asignaciones.get(idPaciente);
    }

    // Version compatible para menus que esperan un resultado booleano.
    public boolean finalizarAtencion(int idPaciente) {
        try {
            return finalizarAtencionOExcepcion(idPaciente);
        } catch (AsignacionInvalidaException | MedicoNoDisponibleException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean finalizarAtencionOExcepcion(int idPaciente) {
        String idMedico = asignaciones.get(idPaciente);
        if (idMedico == null) {
            throw new AsignacionInvalidaException(
                "El paciente " + idPaciente + " no tiene asignacion activa.");
        }
        if (!empleados.setDoctorAvailability(idMedico, true)) {
            throw new MedicoNoDisponibleException(
                "No existe el medico " + idMedico + " de esta asignacion.");
        }
        asignaciones.remove(idPaciente);
        System.out.println("Atencion finalizada: medico " + idMedico + " disponible.");
        return true;
    }
}
