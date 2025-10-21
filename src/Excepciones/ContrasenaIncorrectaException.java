package Excepciones;

public class ContrasenaIncorrectaException extends Exception {
    public ContrasenaIncorrectaException(String mensaje) {
        super(mensaje);
    }
}
