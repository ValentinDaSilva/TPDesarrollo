package Gestores;

import ClasesDAO.UsuarioDAO;
import ClasesDAO.UsuarioDAOImp;
import ClasesDeDominio.Usuario;
import ClasesDTO.UsuarioDTO;
import Excepciones.UsuarioNoExistenteException;
import Excepciones.ContrasenaIncorrectaException;

public class GestorUsuarios {

    private UsuarioDAO dao;

    public GestorUsuarios() {
        this.dao = new UsuarioDAOImp();
    }

    public Usuario autenticar(String nombreUsuario, String contrasena)
        throws UsuarioNoExistenteException, ContrasenaIncorrectaException {

        UsuarioDTO usuario = dao.getUsuario(nombreUsuario);

        if (usuario == null) {
            throw new UsuarioNoExistenteException("El usuario '" + nombreUsuario + "' no existe.");
        }

        if (!usuario.getPassword().equals(contrasena)) {
            throw new ContrasenaIncorrectaException("Contraseña incorrecta para el usuario '" + nombreUsuario + "'.");
        }

        Usuario usuarioDominio = new Usuario(nombreUsuario);

        return usuarioDominio;
    }
}
