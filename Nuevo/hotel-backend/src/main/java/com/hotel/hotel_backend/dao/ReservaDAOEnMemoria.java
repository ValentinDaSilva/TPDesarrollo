package com.hotel.hotel_backend.dao;

import com.hotel.hotel_backend.domain.Reserva;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReservaDAOEnMemoria implements ReservaDAO {

    private static ReservaDAOEnMemoria instance;
    private final List<Reserva> reservas = new ArrayList<>();
    private int generadorId = 1;

    private ReservaDAOEnMemoria() {
        // Constructor privado para prevenir instanciación directa
    }

    public static synchronized ReservaDAOEnMemoria getInstance() {
        if (instance == null) {
            instance = new ReservaDAOEnMemoria();
        }
        return instance;
    }

    @Override
    public void save(Reserva r) {
        r.setId(generadorId++);
        reservas.add(r);
    }

    @Override
    public Optional<Reserva> findById(int id) {
        return reservas.stream()
                .filter(r -> r.getId() == id)
                .findFirst();
    }

    @Override
    public List<Reserva> findAll() {
        return new ArrayList<>(reservas);
    }

    @Override
    public void update(Reserva r) {
        for (int i = 0; i < reservas.size(); i++) {
            if (reservas.get(i).getId() == r.getId()) {
                reservas.set(i, r);
                return;
            }
        }
    }

    @Override
    public void delete(int id) {
        reservas.removeIf(r -> r.getId() == id);
    }

    @Override
    public List<Reserva> findBetween(LocalDate inicio, LocalDate fin) {

        return reservas.stream()
                .filter(r -> {
                    LocalDate ri = r.getFechaInicio();
                    LocalDate rf = r.getFechaFin();

                    // Se solapan:
                    return !(fin.isBefore(ri) || inicio.isAfter(rf));
                })
                .toList();
    }

}
