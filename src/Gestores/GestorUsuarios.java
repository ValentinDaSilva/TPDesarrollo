package Gestores;

public class GestorUsuarios {
    public bool autenticarUsuario(String nombreUsuario, String contrasena) {
        // Ajusta el DAO/servicio según tu proyecto
        Usuario usuario = UsuarioDAO.obtenerPorNombre(nombreUsuario);
        if (usuario == null) {
            throw new UsuarioNoExistenteException("El Usuario '" + nombreUsuario + "' no existe");
        }
        // Si usas hashing de contraseñas, compara el hash en vez de la cadena directa
        return contrasena != null && contrasena.equals(usuario.getContrasena());
       
    }
}
