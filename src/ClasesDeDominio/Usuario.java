package ClasesDeDominio;

public class Usuario {
    private String nombreUsuario;
    private String password;

    // Constructor completo
    public Usuario(String nombreUsuario, String password) {
        this.nombreUsuario = nombreUsuario;
        this.password = password;
    }

    // ===== Getters =====
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean verificarPassword(String password) {
        return this.password.equals(password);
    }

    public boolean cambiarPassword(String passwordActual, String nuevaPassword) {
        if (verificarPassword(passwordActual)) {
            setPassword(nuevaPassword);
            return true;
        }
        return false;
    }
    
     public boolean igualNombreUsuario(String nombreUsuario) {
        return this.nombreUsuario.equals(nombreUsuario);
     }
}
