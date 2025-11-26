package com.hotel.hotel_backend.dao;

import com.hotel.hotel_backend.domain.Huesped;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HuespedDAOEnMemoria implements HuespedDAO {

    private static HuespedDAOEnMemoria instance;
    private final List<Huesped> huespedes = new ArrayList<>();

    private HuespedDAOEnMemoria() {
        // Constructor privado para prevenir instanciación directa
    }

    public static synchronized HuespedDAOEnMemoria getInstance() {
        if (instance == null) {
            instance = new HuespedDAOEnMemoria();
        }
        return instance;
    }

    @Override
    public void save(Huesped h) {
        huespedes.add(h);
    }

    @Override
    public Optional<Huesped> findByNumeroDocumento(String nroDoc) {
        return huespedes.stream()
                .filter(h -> h.getNumeroDocumento().equalsIgnoreCase(nroDoc))
                .findFirst();
    }

    @Override
    public List<Huesped> findAll() {
        return new ArrayList<>(huespedes);
    }

    @Override
    public Optional<Huesped> findByCuit(String cuit) {
        if (cuit == null || cuit.isEmpty()) return Optional.empty();
        return huespedes.stream()
                .filter(h -> cuit.equalsIgnoreCase(h.getCuit()))
                .findFirst();
    }

    @Override
    public void update(Huesped h) {
        for (int i = 0; i < huespedes.size(); i++) {
            if (huespedes.get(i).getNumeroDocumento().equals(h.getNumeroDocumento())) {
                huespedes.set(i, h);
                return;
            }
        }
    }

    

}
