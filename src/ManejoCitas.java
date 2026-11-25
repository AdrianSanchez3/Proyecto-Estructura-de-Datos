import java.util.ArrayList;

public class ManejoCitas{

    private ArrayList<Cita> citas;
    public ManejoCitas(){
        citas = new ArrayList<Cita>();
    }

    public boolean crearCita(Cita nuevaCita){

        if (nuevaCita == null){
            return false;
        }

        //Verifica que no haya una cita con el mismo ID 
        for (Cita cita : citas){
            if (cita.getId() == nuevaCita.getId()){
                return false;
            }
        }

        citas.add(nuevaCita);
        return true;
    }

    public Cita buscarCita(int id){

        for (Cita cita : citas){
            if (cita.getId() == id) {
                return cita;
            }
        }

        return null;
    }

    // Cancela una cita 
    public boolean cancelarCita(int id) {

        Cita cita = buscarCita(id);

        if (cita == null || cita.isCancelada() || cita.isCompletada()){
            return false;
        }

        cita.cancelarCita();
        return true;
    }


    public void mostrarCitas() {

        for (Cita cita : citas) {
            System.out.println(cita);
            System.out.println("--------------------");
        }
    }
} 