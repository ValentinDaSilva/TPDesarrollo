package ClasesDeDominio;

public class Usuario {
    private String nombreUsuario;
    private String password;
    private String rol;

    // Constructor vacío (necesario para Gson o Jackson)
    public Usuario() {}

    // Constructor completo
    public Usuario(String nombreUsuario, String password, String rol) {
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.rol = rol;
    }

    // ===== Getters =====
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public String getRol() {
        return rol;
    }

    // ===== Setters =====
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

}
