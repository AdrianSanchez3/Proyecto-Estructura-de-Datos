/** Excepcion de negocio para el sistema de consultas medicas. */
public class FilaVaciaException extends RuntimeException {
    public FilaVaciaException(String mensaje) {
        super(mensaje);
    }
}
