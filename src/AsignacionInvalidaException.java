/** Excepcion de negocio para el sistema de consultas medicas. */
public class AsignacionInvalidaException extends RuntimeException {
    public AsignacionInvalidaException(String mensaje) {
        super(mensaje);
    }
}
