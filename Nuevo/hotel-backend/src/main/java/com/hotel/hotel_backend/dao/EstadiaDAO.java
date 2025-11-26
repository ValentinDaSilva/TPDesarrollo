package com.hotel.hotel_backend.dao;

import com.hotel.hotel_backend.domain.Estadia;
import java.util.List;
import java.util.Optional;

public interface EstadiaDAO {

    void save(Estadia e);

    Optional<Estadia> findById(int id);

    List<Estadia> findAll();

    void update(Estadia e);

    Optional<Estadia> findActivaByHabitacion(int numeroHabitacion);

}
