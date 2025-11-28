package com.hotel.hotel_backend.service;

import com.hotel.hotel_backend.dao.EstadiaDAO;
import com.hotel.hotel_backend.dao.ReservaDAO;
import com.hotel.hotel_backend.domain.Estadia;
import com.hotel.hotel_backend.domain.Reserva;
import com.hotel.hotel_backend.dto.*;
import com.hotel.hotel_backend.service.Mapeo.MapearADTO;
import com.hotel.hotel_backend.service.Mapeo.MapearADominio;


public class EstadiaService {

    private final EstadiaDAO estadiaDAO;
    private final ReservaDAO reservaDAO;
    private final HabitacionService habitacionService;

    public EstadiaService(EstadiaDAO estadiaDAO,
                          ReservaDAO reservaDAO,
                          HabitacionService habitacionService) {
        this.estadiaDAO = estadiaDAO;
        this.reservaDAO = reservaDAO;
        this.habitacionService = habitacionService;
    }
    
    public String generarEstadia(EstadiaDTO dto) {

        int idReserva = dto.getReserva().getId();

        Reserva reserva = reservaDAO.findById(idReserva)
                .orElseThrow(() -> new RuntimeException("No existe la reserva con id " + idReserva));

        for (Integer nro : reserva.getHabitaciones()) {
            var hab = habitacionService.buscarPorNumero(nro);
            if (hab == null)
                throw new RuntimeException("La habitación " + nro + " no existe.");
            if (!hab.getEstado().equalsIgnoreCase("Reservada"))
                throw new RuntimeException("La habitación " + nro + " no está reservada para generar la estadía.");
        }

        
        reserva.setEstado("Confirmada");
        reservaDAO.update(reserva);

        // Ocupamos habitaciones
        for (Integer nro : reserva.getHabitaciones()) {
            habitacionService.cambiarEstado(nro, "Ocupada");
        }

        
        estadiaDAO.save(MapearADominio.mapearEstadia(dto));
        return "Estadia generada correctamente.";
    }

    public EstadiaDTO obtenerEstadiaActivaPorHabitacion(int numeroHabitacion) {

        Estadia estadia = estadiaDAO.findActivaByHabitacion(numeroHabitacion)
                .orElseThrow(() -> new RuntimeException(
                        "La habitación " + numeroHabitacion + " no tiene estadía activa."
                ));

        return MapearADTO.mapearEstadia(estadia);
    }

    
}
