package Gestores;

import ClasesDAO.DAOFactory;
import ClasesDAO.UsuarioDAO;
import ClasesDeDominio.Usuario;
import ClasesDTO.UsuarioDTO;
import Excepciones.UsuarioNoExistenteException;
import Excepciones.ContrasenaIncorrectaException;

public class GestorUsuarios {
    
    private final UsuarioDAO dao;

    public GestorUsuarios() {
        this.dao = DAOFactory.getInstancia().getUsuarioDAO();
    }

    public Usuario autenticar(String nombreUsuario, String contrasena)
            throws UsuarioNoExistenteException, ContrasenaIncorrectaException {

        UsuarioDTO usuario = dao.getUsuario(nombreUsuario);

        if (usuario == null) {
            throw new UsuarioNoExistenteException(
                "El usuario '" + nombreUsuario + "' no existe."
            );
        }

        if (!usuario.getPassword().equals(contrasena)) {
            throw new ContrasenaIncorrectaException(
                "Contraseña incorrecta para el usuario '" + nombreUsuario + "'."
            );
        }

        // 🔹 Se crea el objeto de dominio
        Usuario usuarioDominio = new Usuario(nombreUsuario);

        return usuarioDominio;
    }
}
