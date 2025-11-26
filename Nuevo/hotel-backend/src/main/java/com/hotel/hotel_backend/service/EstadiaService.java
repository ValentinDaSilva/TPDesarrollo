package com.hotel.hotel_backend.service;

import com.hotel.hotel_backend.dao.EstadiaDAO;
import com.hotel.hotel_backend.dao.ReservaDAO;
import com.hotel.hotel_backend.domain.Estadia;
import com.hotel.hotel_backend.domain.Huesped;
import com.hotel.hotel_backend.domain.Reserva;
import com.hotel.hotel_backend.dto.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

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

    // ==============================================================
    //                GENERAR ESTADIA (CHECK-IN)
    // ==============================================================
    public EstadiaDTO generarEstadia(EstadiaDTO dto) {

        if (dto.getReserva() == null || dto.getReserva().getId() == null) {
            throw new RuntimeException("Debe indicar una reserva válida para generar la estadía.");
        }

        int idReserva = dto.getReserva().getId();

        Reserva reserva = reservaDAO.findById(idReserva)
                .orElseThrow(() -> new RuntimeException("No existe la reserva con id " + idReserva));

        LocalDate checkIn = parsearFecha(dto.getFechaCheckIn(), "fechaCheckIn");
        LocalDate checkOut = dto.getFechaCheckOut() != null && !dto.getFechaCheckOut().isEmpty()
                ? parsearFecha(dto.getFechaCheckOut(), "fechaCheckOut")
                : reserva.getFechaFin();

        // Validación del rango
        if (checkIn.isBefore(reserva.getFechaInicio()) || checkIn.isAfter(reserva.getFechaFin())) {
            throw new RuntimeException("La fecha de check-in no está dentro del rango de la reserva.");
        }

        // Validar habitaciones reservadas
        for (Integer nro : reserva.getHabitaciones()) {
            var hab = habitacionService.buscarPorNumero(nro);
            if (hab == null)
                throw new RuntimeException("La habitación " + nro + " no existe.");
            if (!hab.getEstado().equalsIgnoreCase("Reservada"))
                throw new RuntimeException("La habitación " + nro + " no está reservada para generar la estadía.");
        }

        // Cambiar reserva a Confirmada
        reserva.setEstado("Confirmada");
        reservaDAO.update(reserva);

        // Ocupamos habitaciones
        for (Integer nro : reserva.getHabitaciones()) {
            habitacionService.cambiarEstado(nro, "Ocupada");
        }

        // TITULAR Y ACOMPAÑANTES DEL DOMINIO
        Huesped titularDom = Huesped.mapearHuespedDominio(dto.getTitular());

        List<Huesped> acompDom = new ArrayList<>();
        if (dto.getAcompaniantes() != null) {
            for (HuespedDTO h : dto.getAcompaniantes()) {
                acompDom.add(Huesped.mapearHuespedDominio(h));
            }
        }

        // Crear estadía dominio
        Estadia estadia = new Estadia(
                0,
                checkIn,
                checkOut,
                "EnCurso",
                reserva.getId(),
                titularDom,
                acompDom
        );

        estadiaDAO.save(estadia);

        // DTO respuesta
        EstadiaDTO respuesta = mapearEstadiaADTO(estadia);
        return respuesta;
    }

    // ==============================================================
    //           BUSCAR ESTADÍA ACTIVA POR Nº HABITACIÓN
    // ==============================================================
    public EstadiaDTO obtenerEstadiaActivaPorHabitacion(int numeroHabitacion) {

        Estadia estadia = estadiaDAO.findActivaByHabitacion(numeroHabitacion)
                .orElseThrow(() -> new RuntimeException(
                        "La habitación " + numeroHabitacion + " no tiene estadía activa."
                ));

        return mapearEstadiaADTO(estadia);
    }

    // ==============================================================
    //                         AUXILIARES
    // ==============================================================

    private LocalDate parsearFecha(String f, String campo) {
        if (f == null || f.isEmpty())
            throw new RuntimeException("El campo " + campo + " es obligatorio.");
        try {
            return LocalDate.parse(f);
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Formato inválido en " + campo + ". Use yyyy-MM-dd");
        }
    }

    // ------------------- MAPEO ESTADIA → DTO ---------------------

    private EstadiaDTO mapearEstadiaADTO(Estadia e) {

        EstadiaDTO dto = new EstadiaDTO();
        dto.setId(e.getId());
        dto.setFechaCheckIn(e.getFechaCheckIn().toString());
        dto.setFechaCheckOut(e.getFechaCheckOut() == null ? null : e.getFechaCheckOut().toString());
        dto.setEstado(e.getEstado());

        // Mapeo reserva completa (habitaciones completas también)
        Reserva reserva = reservaDAO.findById(e.getIdReserva()).get();
        ReservaDTO reservaDTO = mapearReservaADTO(reserva);
        dto.setReserva(reservaDTO);

        // Titular
        dto.setTitular(Huesped.mapearHuespedDTO(e.getTitular()));

        // Acompañantes
        List<HuespedDTO> acompDTO = new ArrayList<>();
        for (Huesped h : e.getAcompaniantes()) {
            acompDTO.add(Huesped.mapearHuespedDTO(h));
        }
        dto.setAcompaniantes(acompDTO);

        // Consumos (vacío por ahora)
        dto.setConsumos(List.of());

        return dto;
    }

    // ------------------- MAPEO RESERVA → DTO ---------------------

    private ReservaDTO mapearReservaADTO(Reserva r) {

        TitularDTO titularDTO = new TitularDTO(
                r.getTitular().getNombre(),
                r.getTitular().getApellido(),
                r.getTitular().getTelefono()
        );

        List<HabitacionDTO> habitacionesDTO = r.getHabitaciones().stream()
                .map(nro -> {
                    var hab = habitacionService.buscarPorNumero(nro);
                    HabitacionDTO dto = new HabitacionDTO();
                    dto.setNumero(hab.getNumero());
                    dto.setTipo(hab.getTipo());
                    dto.setCategoria(hab.getCategoria());
                    dto.setCostoPorNoche(hab.getCostoPorNoche());
                    dto.setEstado(hab.getEstado());
                    return dto;
                })
                .toList();

        ReservaDTO dto = new ReservaDTO();
        dto.setId(r.getId());
        dto.setFechaInicio(r.getFechaInicio().toString());
        dto.setFechaFin(r.getFechaFin().toString());
        dto.setEstado(r.getEstado());
        dto.setTitular(titularDTO);
        dto.setHabitaciones(habitacionesDTO);

        return dto;
    }

}
