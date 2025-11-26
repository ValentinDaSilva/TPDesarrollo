package com.hotel.hotel_backend.service;

import com.hotel.hotel_backend.dao.HabitacionDAO;
import com.hotel.hotel_backend.domain.Habitacion;
import com.hotel.hotel_backend.dto.HabitacionDTO;

import java.util.List;

public class HabitacionService {

    private final HabitacionDAO habitacionDAO;

    public HabitacionService(HabitacionDAO habitacionDAO) {
        this.habitacionDAO = habitacionDAO;
    }

    // ==========================================================
    // LISTAR TODAS
    // ==========================================================
    public List<Habitacion> listarTodas() {
        return habitacionDAO.findAll();
    }

    // ==========================================================
    // BUSCAR POR NUMERO (puede devolver null)
    // ==========================================================
    public Habitacion buscarPorNumero(int numero) {
        return habitacionDAO.findByNumero(numero).orElse(null);
    }

    // ==========================================================
    // VALIDAR EXISTENCIA
    // ==========================================================
    public void validarExistencia(int numero) {
        if (habitacionDAO.findByNumero(numero).isEmpty()) {
            throw new RuntimeException("La habitación " + numero + " no existe.");
        }
    }

    // ==========================================================
    // VALIDAR DISPONIBILIDAD
    // ==========================================================
    public void validarDisponible(int numero) {
        Habitacion h = buscarPorNumero(numero);

        if (h == null)
            throw new RuntimeException("La habitación " + numero + " no existe.");

        if (!h.getEstado().equalsIgnoreCase("Disponible")) {
            System.err.println("h.getEstado() = " + h.getEstado());
            throw new RuntimeException("La habitación " + numero + " no está disponible.");
        }
    }

    // ==========================================================
    // CAMBIAR ESTADO
    // ==========================================================
    public void cambiarEstado(int numero, String nuevoEstado) {
        Habitacion h = buscarPorNumero(numero);

        if (h == null)
            throw new RuntimeException("No existe la habitación " + numero);

        h.setEstado(nuevoEstado);
        habitacionDAO.update(h);
    }

    // ==========================================================
    // LISTAR SOLO LAS DISPONIBLES
    // ==========================================================
    public List<Habitacion> listarDisponibles() {
        return habitacionDAO.findAll()
                .stream()
                .filter(h -> h.getEstado().equalsIgnoreCase("Disponible"))
                .toList();
    }

    // ==========================================================
    // VALIDAR LISTA DE HABITACIONES (DTO COMPLETO)
    // ==========================================================
    public void validarHabitacionesDisponibles(List<HabitacionDTO> habitacionesDTO) {
        for (HabitacionDTO dto : habitacionesDTO) {
            int nro = dto.getNumero();
            validarExistencia(nro);
            validarDisponible(nro);
        }
    }
}
