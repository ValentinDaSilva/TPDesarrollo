package com.hotel.hotel_backend.dao;

import com.hotel.hotel_backend.domain.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;


public interface HabitacionDAO extends JpaRepository<Habitacion, Integer> {

    Habitacion findByNumero(Integer numero);
}
