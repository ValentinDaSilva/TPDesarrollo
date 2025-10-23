package Gestores;

import ClasesDAO.UsuarioDAO;
import ClasesDAO.UsuarioDAOImp;
import ClasesDeDominio.Usuario;
import ClasesDTO.UsuarioDTO;
import Excepciones.UsuarioNoExistenteException;
import Excepciones.ContrasenaIncorrectaException;

public class GestorUsuarios {

    private final UsuarioDAO dao;

    public GestorUsuarios() {
        // 🔹 En lugar de crear un DAO nuevo, usamos la instancia única
        this.dao = UsuarioDAOImp.getInstancia();
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

        // 🔹 Transformamos el DTO a la clase de dominio
        Usuario usuarioDominio = new Usuario(nombreUsuario);

        return usuarioDominio;
    }
}
