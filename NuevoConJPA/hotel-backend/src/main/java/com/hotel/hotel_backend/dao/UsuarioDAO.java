package com.hotel.hotel_backend.dao;

import com.hotel.hotel_backend.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioDAO extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByUsername(String username);
}
