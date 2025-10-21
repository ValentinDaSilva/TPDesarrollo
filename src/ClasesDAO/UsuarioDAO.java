package ClasesDAO;
import java.util.List;
import ClasesDeDominio.Usuario;


public interface UsuarioDAO {
    List<Usuario> obtenerTodos();
    Usuario getUsuario(String nombreUsuario);
    void putUsuario(Usuario usuario);
    void updateUsuario(Usuario usuario);
    void deleteUsuario(Usuario usuario);
    void escribirEnArchivo(List<Usuario> usuario);
} 
