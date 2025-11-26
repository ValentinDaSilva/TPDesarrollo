package com.hotel.hotel_backend.dao;

import com.hotel.hotel_backend.domain.Factura;

import java.util.List;
import java.util.Optional;

public interface FacturaDAO {

    void save(Factura f);

    Optional<Factura> findById(int id);

    List<Factura> findAll();

    void update(Factura f);

    List<Factura> findPendientesByHabitacion(int nroHabitacion);
}
