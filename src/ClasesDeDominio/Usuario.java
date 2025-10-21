package ClasesDeDominio;

public class Usuario {
    private String nombreUsuario;

    // Constructor completo
    public Usuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    // ===== Getters =====
    public String getNombreUsuario() {
        return nombreUsuario;
    }


    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

     public boolean igualNombreUsuario(String nombreUsuario) {
        return this.nombreUsuario.equals(nombreUsuario);
     }
}
