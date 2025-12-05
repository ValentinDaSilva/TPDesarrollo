package com.hotel.hotel_backend.Controller;

import com.hotel.hotel_backend.dto.UsuarioDTO;
import com.hotel.hotel_backend.service.UsuarioService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // ============================================================
    //                AUTENTICAR USUARIO (LOGIN)
    // ============================================================
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioDTO dto) {
        try {
            return ResponseEntity.ok(
                    usuarioService.autenticar(dto.getUsername(), dto.getPassword())
            );
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ============================================================
    //                CREAR USUARIO (opcional)
    // ============================================================
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody UsuarioDTO dto) {
        try {
            return ResponseEntity.ok(usuarioService.crearUsuario(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
