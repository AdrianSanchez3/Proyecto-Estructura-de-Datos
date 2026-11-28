/** Excepcion de negocio para el sistema de consultas medicas. */
public class MedicoNoDisponibleException extends RuntimeException {
    public MedicoNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}
