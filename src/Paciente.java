import java.util.ArrayList;

public class Paciente {
    private final int id;
    private final String nombre;
    private final int edad;
    private final ArrayList<Cita> historialClinico;

    public Paciente(int id, String nombre, int edad) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.historialClinico = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public boolean agregarCita(Cita cita) {
        if (cita == null || cita.getIdPaciente() != id) {
            return false;
        }
        for (Cita existente : historialClinico) {
            if (existente.getId() == cita.getId()) {
                return false;
            }
        }
        historialClinico.add(cita);
        return true;
    }

    public void mostrarHistorialClinico() {
        if (historialClinico.isEmpty()) {
            System.out.println("El paciente no tiene citas en su historial.");
            return;
        }
        for (Cita cita : historialClinico) {
            System.out.println(cita);
            System.out.println("--------------------");
        }
    }

    @Override
    public String toString() {
        return "Paciente ID: " + id +
               "\nNombre: " + nombre +
               "\nEdad: " + edad;
    }
}
