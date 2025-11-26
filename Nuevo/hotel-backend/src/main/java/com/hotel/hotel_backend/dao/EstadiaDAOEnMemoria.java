package com.hotel.hotel_backend.dao;

import com.hotel.hotel_backend.domain.Estadia;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EstadiaDAOEnMemoria implements EstadiaDAO {

    private static EstadiaDAOEnMemoria instance;
    private final List<Estadia> estadias = new ArrayList<>();
    private int secuenciaId = 1;

    private EstadiaDAOEnMemoria() {
        // Constructor privado para prevenir instanciación directa
    }

    public static synchronized EstadiaDAOEnMemoria getInstance() {
        if (instance == null) {
            instance = new EstadiaDAOEnMemoria();
        }
        return instance;
    }

    @Override
    public void save(Estadia e) {
        e.setId(secuenciaId++);
        estadias.add(e);
    }

    @Override
    public Optional<Estadia> findById(int id) {
        return estadias.stream()
                .filter(e -> e.getId() == id)
                .findFirst();
    }

    @Override
    public List<Estadia> findAll() {
        return new ArrayList<>(estadias);
    }

    @Override
    public void update(Estadia e) {
        for (int i = 0; i < estadias.size(); i++) {
            if (estadias.get(i).getId() == e.getId()) {
                estadias.set(i, e);
                return;
            }
        }
    }

    @Override
    public Optional<Estadia> findActivaByHabitacion(int numeroHabitacion) {
        ReservaDAO reservaDAO = ReservaDAOEnMemoria.getInstance();
        return estadias.stream()
                // estado activo = EnCurso
                .filter(e -> e.getEstado().equalsIgnoreCase("EnCurso"))
                // y que la reserva contenga esa habitación
                .filter(e -> {
                    var resOpt = reservaDAO.findById(e.getIdReserva());
                    if (resOpt.isEmpty()) return false;
                    var reserva = resOpt.get();
                    return reserva.getHabitaciones().contains(numeroHabitacion);
                })
                .findFirst();
    }


}
