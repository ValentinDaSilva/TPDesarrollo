package com.hotel.hotel_backend.dao;

import com.hotel.hotel_backend.domain.Factura;
import com.hotel.hotel_backend.domain.NotaDeCredito;
import com.hotel.hotel_backend.domain.Pago;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FacturaDAO {

    void save(Factura f);

    Optional<Factura> findById(int id);

    List<Factura> findAll();

    void update(Factura f);

    List<Factura> findPendientesByHabitacion(int nroHabitacion);

    List<Pago> findPagosEntreFechas(LocalDate desde, LocalDate hasta);
    
    List<Pago> findChequesEntreFechas(LocalDate desde, LocalDate hasta);

    void saveNota(NotaDeCredito nota);
}
