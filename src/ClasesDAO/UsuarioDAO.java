package ClasesDAO;
import java.util.List;

import ClasesDTO.UsuarioDTO;


public interface UsuarioDAO {
    List<UsuarioDTO> obtenerTodos();
    UsuarioDTO getUsuario(String nombreUsuario);
    void putUsuario(UsuarioDTO usuario);
    void updateUsuario(UsuarioDTO usuario);
    void deleteUsuario(UsuarioDTO usuario);
    void escribirEnArchivo(List<UsuarioDTO> usuario);
    boolean verificarPassword(UsuarioDTO usuario,String password);
    boolean cambiarPassword(UsuarioDTO usuario, String passwordActual, String nuevaPassword);
} 
