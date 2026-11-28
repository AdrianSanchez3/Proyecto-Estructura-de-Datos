/** Excepcion de negocio para el sistema de consultas medicas. */
public class CitaConflictivaException extends RuntimeException {
    public CitaConflictivaException(String mensaje) {
        super(mensaje);
    }
}
