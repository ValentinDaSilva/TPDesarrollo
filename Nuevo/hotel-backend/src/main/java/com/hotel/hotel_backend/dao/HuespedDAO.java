package com.hotel.hotel_backend.dao;

import com.hotel.hotel_backend.domain.Huesped;

import java.util.List;
import java.util.Optional;

public interface HuespedDAO {

    void save(Huesped h);

    Optional<Huesped> findByNumeroDocumento(String nroDoc);

    Optional<Huesped> findByCuit(String cuit);

    List<Huesped> findAll();
 
    void update(Huesped h);
}
