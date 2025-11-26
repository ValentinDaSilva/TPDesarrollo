package ClasesDTO;
    
public class UsuarioDTO {
    private String nombreUsuario;
    private String password;

    public UsuarioDTO(String nombreUsuario, String password) {
        this.nombreUsuario = nombreUsuario;
        this.password = password;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
