// src/main/java/com/hotel/hotel_backend/service/HabitacionService.java
package com.hotel.hotel_backend.service;

import com.hotel.hotel_backend.dao.HabitacionDAO;
import com.hotel.hotel_backend.domain.Habitacion;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HabitacionService {

    private final HabitacionDAO habitacionDAO;

    public HabitacionService(HabitacionDAO habitacionDAO) {
        this.habitacionDAO = habitacionDAO;
    }

    public List<Habitacion> obtenerTodas() {
        return habitacionDAO.findAll();
    }

    public Habitacion obtenerPorNumero(int numero) {
        return habitacionDAO.findByNumero(numero)
                .orElseThrow(() ->
                        new RuntimeException("No existe la habitación Nº " + numero));
    }

    public void cambiarEstado(int numero, String nuevoEstado) {

        Habitacion h = obtenerPorNumero(numero);
        String actual = normalizar(h.getEstado());
        nuevoEstado = normalizar(nuevoEstado);

        validarTransicion(actual, nuevoEstado, numero);

        h.setEstado(nuevoEstado);
        habitacionDAO.save(h);
    }

    private void validarTransicion(String actual, String nuevo, int nro) {

        switch (actual) {

            case "disponible" -> {
                if (!(nuevo.equals("reservada") || nuevo.equals("fueradeservicio"))) {
                    throw new RuntimeException(
                            "Transición inválida para habitación " + nro +
                                    ": Disponible → solo Reservada o FueraDeServicio."
                    );
                }
            }

            case "reservada" -> {
                if (!(nuevo.equals("ocupada") || nuevo.equals("disponible"))) {
                    throw new RuntimeException(
                            "Transición inválida para habitación " + nro +
                                    ": Reservada → solo Ocupada (check-in) o Disponible (cancelar)."
                    );
                }
            }

            case "ocupada" -> {
                if (!nuevo.equals("disponible")) {
                    throw new RuntimeException(
                            "Transición inválida para habitación " + nro +
                                    ": Ocupada → solo Disponible (check-out)."
                    );
                }
            }

            case "fueradeservicio" -> {
                if (!nuevo.equals("disponible")) {
                    throw new RuntimeException(
                            "Transición inválida para habitación " + nro +
                                    ": FueraDeServicio → solo Disponible."
                    );
                }
            }

            default -> throw new RuntimeException("Estado desconocido: " + actual);
        }
    }

    private String normalizar(String estado) {
        if (estado == null) return "";
        return estado.trim().toLowerCase();
    }
}
