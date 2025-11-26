package com.hotel.hotel_backend.dao;

import com.hotel.hotel_backend.domain.Factura;
import com.hotel.hotel_backend.domain.Estadia;
import com.hotel.hotel_backend.domain.Reserva;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FacturaDAOEnMemoria implements FacturaDAO {

    private static FacturaDAOEnMemoria instance;
    public static synchronized FacturaDAOEnMemoria getInstance() {
        if (instance == null) instance = new FacturaDAOEnMemoria();
        return instance;
    }

    private final List<Factura> facturas = new ArrayList<>();
    private int secuenciaId = 1;

    private FacturaDAOEnMemoria() {}

    @Override
    public void save(Factura f) {
        f.setId(secuenciaId++);
        facturas.add(f);
    }

    @Override
    public Optional<Factura> findById(int id) {
        return facturas.stream()
                .filter(x -> x.getId() == id)
                .findFirst();
    }

    @Override
    public List<Factura> findAll() {
        return new ArrayList<>(facturas);
    }

    @Override
    public void update(Factura f) {
        for (int i = 0; i < facturas.size(); i++) {
            if (facturas.get(i).getId() == f.getId()) {
                facturas.set(i, f);
                return;
            }
        }
    }

    // ------------------------------------------------------
    // BUSCAR FACTURAS PENDIENTES POR HABITACIÓN
    // ------------------------------------------------------
    @Override
    public List<Factura> findPendientesByHabitacion(int nroHabitacion) {

        return facturas.stream()
                .filter(f -> f.getEstado().equalsIgnoreCase("Pendiente"))
                .filter(f -> estadiaTieneHabitacion(f.getEstadia().getId(), nroHabitacion))
                .collect(Collectors.toList());
    }


    private boolean estadiaTieneHabitacion(int idEstadia, int nroHabitacion) {

        Estadia e = EstadiaDAOEnMemoria.getInstance()
                .findById(idEstadia)
                .orElse(null);

        if (e == null) return false;

        Reserva r = ReservaDAOEnMemoria.getInstance()
                .findById(e.getIdReserva())
                .orElse(null);

        if (r == null) return false;

        return r.getHabitaciones().contains(nroHabitacion);
    }
}
