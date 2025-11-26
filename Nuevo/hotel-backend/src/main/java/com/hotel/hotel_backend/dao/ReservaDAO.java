package com.hotel.hotel_backend.dao;

import com.hotel.hotel_backend.domain.Reserva;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservaDAO {

    void save(Reserva r);

    Optional<Reserva> findById(int id);

    List<Reserva> findAll();

    void update(Reserva r);

    void delete(int id);
    
    List<Reserva> findBetween(LocalDate inicio, LocalDate fin);

}
