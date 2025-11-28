// [src/main/java/com/hotel/hotel_backend/service/EstadiaService.java]
package com.hotel.hotel_backend.service;

import com.hotel.hotel_backend.dao.EstadiaDAO;
import com.hotel.hotel_backend.dao.ReservaDAO;
import com.hotel.hotel_backend.dao.HuespedDAO;
import com.hotel.hotel_backend.dao.HabitacionDAO;

import com.hotel.hotel_backend.domain.Estadia;
import com.hotel.hotel_backend.domain.Habitacion;
import com.hotel.hotel_backend.domain.Huesped;
import com.hotel.hotel_backend.domain.Reserva;

import com.hotel.hotel_backend.dto.EstadiaDTO;
import com.hotel.hotel_backend.dto.HuespedDTO;
import com.hotel.hotel_backend.service.Mapeo.MapearADTO;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EstadiaService {

    private final EstadiaDAO estadiaDAO;
    private final ReservaDAO reservaDAO;
    private final HabitacionDAO habitacionDAO;
    private final HuespedDAO huespedDAO;

    public EstadiaService(
            EstadiaDAO estadiaDAO,
            ReservaDAO reservaDAO,
            HabitacionDAO habitacionDAO,
            HuespedDAO huespedDAO
    ) {
        this.estadiaDAO = estadiaDAO;
        this.reservaDAO = reservaDAO;
        this.habitacionDAO = habitacionDAO;
        this.huespedDAO = huespedDAO;
    }

    // ==========================================================
    //                        GENERAR ESTADÍA (CHECK-IN)
    // ==========================================================
    public String generarEstadia(EstadiaDTO dto) {

        if (dto == null || dto.getReserva() == null || dto.getReserva().getId() == null)
            throw new RuntimeException("Debe enviar una reserva válida para generar la estadía.");

        Reserva reserva = reservaDAO.findById(dto.getReserva().getId())
                .orElseThrow(() -> new RuntimeException(
                        "No existe la reserva con ID " + dto.getReserva().getId()
                ));

        // Validar habitaciones: deben estar Reservadas
        for (Habitacion h : reserva.getHabitaciones()) {
            if (!h.getEstado().equalsIgnoreCase("Reservada")) {
                throw new RuntimeException(
                        "La habitación " + h.getNumero() + " no está reservada para esta estadía."
                );
            }
        }

        // Validar titular
        if (dto.getTitular() == null || dto.getTitular().getNumeroDocumento() == null)
            throw new RuntimeException("Debe enviar el titular con número de documento.");

        Huesped titular = huespedDAO.findByNumeroDocumento(dto.getTitular().getNumeroDocumento())
                .orElseThrow(() -> new RuntimeException(
                        "No existe huésped con documento " + dto.getTitular().getNumeroDocumento()
                ));

        // Acompañantes
        List<Huesped> acompaniantes = new ArrayList<>();
        if (dto.getAcompaniantes() != null) {
            for (HuespedDTO hDto : dto.getAcompaniantes()) {
                Huesped h = huespedDAO.findByNumeroDocumento(hDto.getNumeroDocumento())
                        .orElseThrow(() -> new RuntimeException(
                                "No existe acompañante con documento " + hDto.getNumeroDocumento()
                        ));
                acompaniantes.add(h);
            }
        }

        // Cambiar estado reserva → Confirmada
        reserva.setEstado("Confirmada");
        reservaDAO.save(reserva);

        // Cambiar estado habitaciones → Ocupada
        for (Habitacion hab : reserva.getHabitaciones()) {
            hab.setEstado("Ocupada");
            habitacionDAO.save(hab);
        }

        // Crear Estadía
        Estadia estadia = new Estadia();
        estadia.setId(dto.getId()); // puede venir null
        estadia.setFechaCheckIn(LocalDate.now());
        estadia.setHoraCheckIn(LocalTime.now());
        estadia.setFechaCheckOut(null);
        estadia.setHoraCheckOut(null);
        estadia.setEstado("EnCurso");

        estadia.setReserva(reserva);
        estadia.setTitular(titular);
        estadia.setAcompaniantes(acompaniantes);

        estadiaDAO.save(estadia);

        return "Estadía generada correctamente.";
    }

    // ==========================================================
    //              OBTENER ESTADÍA ACTIVA POR HABITACIÓN
    // ==========================================================
    public EstadiaDTO obtenerEstadiaActivaPorHabitacion(int numeroHabitacion) {

        Estadia estadia = estadiaDAO.findActivaByHabitacion(numeroHabitacion)
                .orElseThrow(() -> new RuntimeException(
                        "La habitación " + numeroHabitacion + " no tiene estadía activa."
                ));

        return MapearADTO.mapearEstadia(estadia);
    }

    // ==========================================================
    //                        CHECK-OUT
    // ==========================================================
    public EstadiaDTO registrarCheckOut(Integer idEstadia) {

        Estadia estadia = estadiaDAO.findById(idEstadia)
                .orElseThrow(() ->
                        new RuntimeException("No existe la estadía con ID " + idEstadia)
                );

        if (!"EnCurso".equalsIgnoreCase(estadia.getEstado())) {
            throw new RuntimeException("La estadía ya está finalizada.");
        }

        estadia.setFechaCheckOut(LocalDate.now());
        estadia.setHoraCheckOut(LocalTime.now());
        estadia.setEstado("Finalizada");

        // Volver habitaciones a disponible
        for (Habitacion h : estadia.getReserva().getHabitaciones()) {
            h.setEstado("Disponible");
            habitacionDAO.save(h);
        }

        estadiaDAO.save(estadia);

        return MapearADTO.mapearEstadia(estadia);
    }
}
