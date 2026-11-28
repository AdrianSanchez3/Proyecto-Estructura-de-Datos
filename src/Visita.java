public class Visita {
    
    private String idMedico;
    private int idPaciente;
    private String fecha;
    

    public Visita(int idPaciente,
              String idMedico,
              String fecha){
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.fecha= fecha;

    }
    public int getIdPaciente(){
        return idPaciente;
    }
    public String getIdMedico(){
        return idMedico;
    }

    public String getFecha(){
        return fecha;
    }

    @Override
    public String toString(){
        return "ID del paciente: "+ idPaciente +
               "\nID del medico: " + idMedico +
               "\nFecha: " + fecha;
    }
}