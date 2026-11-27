public class Cita{

    private int id;
    private int idPaciente;
    private int idMedico;
    private String fecha;
    private String hora;
    private String motivo;
    private boolean completada;
    private boolean cancelada;

    public Cita(int id, int idPaciente, int idMedico,String fecha, String hora, String motivo){

        this.id = id;
        this.idPaciente = idPaciente;
        this.idMedico= idMedico;
        this.fecha = fecha;
        this.hora = hora;
        this.motivo = motivo;
        this.completada = false;
        this.cancelada= false;
    }

    public int getId(){
        return id;
    }

    public int getIdPaciente(){
        return idPaciente;
    }

    public int getIdMedico(){
        return idMedico;
    }

    public String getFecha(){
        return fecha;
    }

    public String getHora(){
        return hora;
    }

    public String getMotivo(){
        return motivo;
    }

    public boolean isCompletada(){
        return completada;
    }

    public boolean isCancelada(){
        return cancelada;
    }


    public void completarCita(){
        if (!cancelada) {
            completada= true;
        }
    }

    
    public void cancelarCita(){
        if (!completada) {
            cancelada= true;
        }
    }

    @Override
    public String toString(){
        return "ID de cita: " + id +
               "\nID del paciente: " + idPaciente +
               "\nID del medico: " + idMedico +
               "\nFecha: " + fecha +
               "\nHora: " + hora +
               "\nMotivo: " + motivo +
               "\nCompletada: " + completada +
               "\nCancelada: " + cancelada;
    }
} 