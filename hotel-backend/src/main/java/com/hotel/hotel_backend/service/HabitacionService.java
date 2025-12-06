// src/main/java/com/hotel/hotel_backend/service/HabitacionService.java
package com.hotel.hotel_backend.service;

import com.hotel.hotel_backend.dao.HabitacionDAO;
import com.hotel.hotel_backend.domain.Habitacion;
import com.hotel.hotel_backend.dto.HabitacionDTO;
import com.hotel.hotel_backend.service.Mapeo.MapearADTO;
import com.hotel.hotel_backend.service.Mapeo.MapearADominio;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HabitacionService {

    private final HabitacionDAO habitacionDAO;

    public HabitacionService(HabitacionDAO habitacionDAO) {
        this.habitacionDAO = habitacionDAO;
    }

    public List<HabitacionDTO> obtenerTodas() {
        return habitacionDAO.findAll()
            .stream()
            .map(MapearADTO::mapearHabitacion)
            .toList();
    }

    public HabitacionDTO obtenerPorNumero(int numero) {
        return MapearADTO.mapearHabitacion(habitacionDAO.findByNumero(numero));
    }

    public void cambiarEstado(int numero, String nuevoEstado) {

        Habitacion h = MapearADominio.mapearHabitacion( obtenerPorNumero(numero));
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
