package com.hotel.hotel_backend.service;

import com.hotel.hotel_backend.dao.UsuarioDAO;
import com.hotel.hotel_backend.domain.Usuario;
import com.hotel.hotel_backend.dto.UsuarioDTO;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioDAO usuarioDAO;

    public UsuarioService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    // ============================================================
    //                  AUTENTICAR USUARIO (CU01)
    // ============================================================
    public UsuarioDTO autenticar(String username, String password) {

        Usuario usuario = usuarioDAO.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));

        if (!usuario.getPassword().equals(password)) {
            throw new RuntimeException("Contraseña incorrecta.");
        }

        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setUsername(usuario.getUsername());
        dto.setRol(usuario.getRol());
        dto.setPassword(null); // nunca devolver password

        return dto;
    }

    // ============================================================
    //                 CREAR USUARIO (opcional)
    // ============================================================
    public UsuarioDTO crearUsuario(UsuarioDTO dto) {

        Usuario u = new Usuario();
        u.setUsername(dto.getUsername());
        u.setPassword(dto.getPassword());
        u.setRol(dto.getRol());

        usuarioDAO.save(u);

        dto.setId(u.getId());
        dto.setPassword(null);

        return dto;
    }
}
