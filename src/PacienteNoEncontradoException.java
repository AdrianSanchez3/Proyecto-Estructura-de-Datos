/** Excepcion de negocio para el sistema de consultas medicas. */
public class PacienteNoEncontradoException extends RuntimeException {
    public PacienteNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
