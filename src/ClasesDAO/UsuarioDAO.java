package ClasesDAO;
import java.util.List;
import ClasesDeDominio.Usuario;


public interface UsuarioDAO {
    List<Usuario> obtenerTodos();
    Usuario getHuesped(String nombreUsuario);
    void putHuesped(Usuario usuario);
    void updateHuesped(Usuario usuario);
    void deleteHuesped(Usuario usuario);
    void escribirEnArchivo(List<Usuario> usuario);
} 
