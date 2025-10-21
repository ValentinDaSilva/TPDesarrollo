package Excepciones;

public class UsuarioNoExistenteException extends Exception {
    public UsuarioNoExistenteException(String mensaje) {
        super(mensaje);
    }
}
