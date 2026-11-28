import java.util.ArrayList;

public class ManejoCitas {
    private final ArrayList<Cita> citas;

    public ManejoCitas() {
        citas = new ArrayList<>();
    }

    // Se conserva el contrato booleano del codigo original.
    public boolean crearCita(Cita nuevaCita) {
        try {
            return crearCitaOExcepcion(nuevaCita);
        } catch (IllegalArgumentException | CitaConflictivaException e) {
            return false;
        }
    }

    public boolean crearCitaOExcepcion(Cita nuevaCita) {
        if (nuevaCita == null) {
            throw new IllegalArgumentException("No se puede registrar una cita nula.");
        }
        if (nuevaCita.getId() <= 0 || nuevaCita.getIdPaciente() <= 0 || nuevaCita.getIdMedico() == null || nuevaCita.getIdMedico().trim().isEmpty()
                || nuevaCita.getFecha() == null || nuevaCita.getFecha().trim().isEmpty()
                || nuevaCita.getHora() == null || nuevaCita.getHora().trim().isEmpty()) {
            throw new IllegalArgumentException("La cita contiene IDs, fecha u hora invalidos.");
        }

        for (Cita existente : citas) {
            if (existente.getId() == nuevaCita.getId()) {
                throw new CitaConflictivaException("Ya existe una cita con ID " + nuevaCita.getId());
            }
            // Una cita cancelada ya no reserva su horario. Una completada si lo reserva.
            if (!existente.isCancelada()
                    && existente.getFecha().equals(nuevaCita.getFecha())
                    && existente.getHora().equals(nuevaCita.getHora())) {
                if (existente.getIdMedico().equals(nuevaCita.getIdMedico())) {
                    throw new CitaConflictivaException("El medico ya tiene una cita en ese horario.");
                }
                if (existente.getIdPaciente() == nuevaCita.getIdPaciente()) {
                    throw new CitaConflictivaException("El paciente ya tiene una cita en ese horario.");
                }
            }
        }
        citas.add(nuevaCita);
        return true;
    }

    public Cita buscarCita(int id) {
        for (Cita cita : citas) {
            if (cita.getId() == id) {
                return cita;
            }
        }
        return null;
    }

    public boolean cancelarCita(int id) {
        Cita cita = buscarCita(id);
        if (cita == null || cita.isCancelada() || cita.isCompletada()) {
            return false;
        }
        cita.cancelarCita();
        return true;
    }
    
    public boolean completarCita(int id, ManejoPacientes manejoPacientes) {

    Cita cita = buscarCita(id);

    if (cita == null || cita.isCancelada() || cita.isCompletada()
            || manejoPacientes == null) {
        return false;
    }

    Paciente paciente = manejoPacientes.buscarPaciente(cita.getIdPaciente());

    if (paciente == null){
        return false;
    }

        cita.completarCita();

    if (!manejoPacientes.registrarVisita(cita)) {
        return false;
    }

    return true;
    }
    /** Muestra las citas no canceladas para una fecha YYYY-MM-DD. */
    public int mostrarCitasDeFecha(String fecha) {
        int cantidad = 0;
        for (Cita cita : citas) {
            if (!cita.isCancelada() && cita.getFecha().equals(fecha)) {
                System.out.println(cita);
                System.out.println("--------------------");
                cantidad++;
            }
        }
        if (cantidad == 0) {
            System.out.println("No hay citas para la fecha " + fecha + ".");
        }
        return cantidad;
    }

    public void mostrarCitas() {
        for (Cita cita : citas) {
            System.out.println(cita);
            System.out.println("--------------------");
        }
    }
}
