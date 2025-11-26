package com.hotel.hotel_backend.dao;

import com.hotel.hotel_backend.domain.Habitacion;
import java.util.List;
import java.util.Optional;

public interface HabitacionDAO {

    void save(Habitacion h);

    Optional<Habitacion> findByNumero(int numero);

    List<Habitacion> findAll();

    void update(Habitacion h);
}
