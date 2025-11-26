package com.hotel.hotel_backend.dao;

import com.hotel.hotel_backend.domain.Habitacion;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HabitacionDAOEnMemoria implements HabitacionDAO {

    private static HabitacionDAOEnMemoria instance;
    private final List<Habitacion> habitaciones = new ArrayList<>();

    private HabitacionDAOEnMemoria() {
        // Precargar habitaciones según el enunciado
        habitaciones.add(new Habitacion(101, "Individual", "Estandar", 50800, "Disponible"));
        habitaciones.add(new Habitacion(102, "Individual", "Estandar", 50800, "Disponible"));
        habitaciones.add(new Habitacion(201, "Doble", "Estandar", 70230, "Disponible"));
        habitaciones.add(new Habitacion(202, "Doble", "Estandar", 70230, "Disponible"));
        // ...podés agregar el resto más adelante
    }

    public static synchronized HabitacionDAOEnMemoria getInstance() {
        if (instance == null) {
            instance = new HabitacionDAOEnMemoria();
        }
        return instance;
    }

    @Override
    public void save(Habitacion h) {
        habitaciones.add(h);
    }

    @Override
    public Optional<Habitacion> findByNumero(int numero) {
        return habitaciones.stream()
                .filter(h -> h.getNumero() == numero)
                .findFirst();
    }

    @Override
    public List<Habitacion> findAll() {
        return new ArrayList<>(habitaciones);
    }

    @Override
    public void update(Habitacion h) {
        for (int i = 0; i < habitaciones.size(); i++) {
            if (habitaciones.get(i).getNumero() == h.getNumero()) {
                habitaciones.set(i, h);
                return;
            }
        }
    }
}
