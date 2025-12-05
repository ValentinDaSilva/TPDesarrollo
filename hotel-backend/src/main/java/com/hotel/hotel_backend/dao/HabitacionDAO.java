package com.hotel.hotel_backend.dao;

import com.hotel.hotel_backend.domain.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HabitacionDAO extends JpaRepository<Habitacion, Integer> {

    Optional<Habitacion> findByNumero(Integer numero);
}
