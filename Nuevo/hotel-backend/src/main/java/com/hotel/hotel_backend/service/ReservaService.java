package com.hotel.hotel_backend.service;

import com.hotel.hotel_backend.dao.ReservaDAO;
import com.hotel.hotel_backend.domain.Reserva;
import com.hotel.hotel_backend.domain.Titular;
import com.hotel.hotel_backend.dto.HabitacionDTO;
import com.hotel.hotel_backend.dto.ReservaDTO;
import com.hotel.hotel_backend.dto.TitularDTO;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ReservaService {

    private final ReservaDAO reservaDAO;
    private final HabitacionService habitacionService;

    public ReservaService(ReservaDAO reservaDAO, HabitacionService habitacionService) {
        this.reservaDAO = reservaDAO;
        this.habitacionService = habitacionService;
    }

    public ReservaDTO reservarHabitacion(ReservaDTO dto) {

        LocalDate inicio = parsearFecha(dto.getFechaInicio(), "fechaInicio");
        LocalDate fin = parsearFecha(dto.getFechaFin(), "fechaFin");


        if (dto.getHabitaciones() == null || dto.getHabitaciones().isEmpty()) {
            throw new RuntimeException("Debe seleccionar al menos una habitación.");
        }

        habitacionService.validarHabitacionesDisponibles(dto.getHabitaciones());

        List<Integer> numeros = dto.getHabitaciones()
                .stream()
                .map(HabitacionDTO::getNumero)
                .toList();

        Titular titular = new Titular(
                dto.getTitular().getNombre(),
                dto.getTitular().getApellido(),
                dto.getTitular().getTelefono()
        );

        Reserva reserva = new Reserva(
                0,
                inicio,
                fin,
                titular,
                numeros,
                "Reservada"
        );

        reservaDAO.save(reserva);

        for (int nro : numeros) {
            habitacionService.cambiarEstado(nro, "Reservada");
        }

        dto.setId(reserva.getId());
        dto.setEstado("Reservada");

        return dto;
    }

    private LocalDate parsearFecha(String f, String campo) {
        if (f == null || f.isEmpty()) {
            throw new RuntimeException("El campo " + campo + " es obligatorio.");
        }
        try {
            return LocalDate.parse(f);
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Formato inválido en " + campo + ". Use yyyy-MM-dd");
        }
    }

    public List<ReservaDTO> listarReservasEntre(String inicioStr, String finStr) {

        LocalDate inicio = parsearFecha(inicioStr, "inicio");
        LocalDate fin = parsearFecha(finStr, "fin");

        if (fin.isBefore(inicio)) {
            throw new RuntimeException("La fecha final no puede ser anterior a la inicial.");
        }

        // DAO hace el trabajo
        List<Reserva> lista = reservaDAO.findBetween(inicio, fin);

        // Mapear a DTO
        return lista.stream()
                .map(this::mapearReservaADTO)
                .toList();
    }

    private ReservaDTO mapearReservaADTO(Reserva r) {

        // Titular
        TitularDTO titularDTO = new TitularDTO(
                r.getTitular().getNombre(),
                r.getTitular().getApellido(),
                r.getTitular().getTelefono()
        );

        // Habitaciones completas reconstruidas desde el DAO
        List<HabitacionDTO> habitacionesDTO = r.getHabitaciones().stream()
                .map(nro -> {
                    var hab = habitacionService.buscarPorNumero(nro); // DOMINIO REAL

                    HabitacionDTO dto = new HabitacionDTO();
                    dto.setNumero(hab.getNumero());
                    dto.setTipo(hab.getTipo());
                    dto.setCategoria(hab.getCategoria());
                    dto.setCostoPorNoche(hab.getCostoPorNoche());
                    dto.setEstado(hab.getEstado());  // estado real (Disponible/Reservada/Ocupada)
                    return dto;
                })
                .toList();

        // Construir reserva completa
        ReservaDTO dto = new ReservaDTO();
        dto.setId(r.getId());
        dto.setFechaInicio(r.getFechaInicio().toString());
        dto.setFechaFin(r.getFechaFin().toString());
        dto.setEstado(r.getEstado());
        dto.setTitular(titularDTO);
        dto.setHabitaciones(habitacionesDTO);

        return dto;
    }

    public ReservaDTO cancelarReserva(ReservaDTO dto) {

        if (dto.getId() == null) {
            throw new RuntimeException("Debe enviar el ID de la reserva a cancelar.");
        }

        int idReserva = dto.getId();

        // Buscar la reserva REAL en memoria
        Reserva reserva = reservaDAO.findById(idReserva)
                .orElseThrow(() -> new RuntimeException("No existe la reserva con id " + idReserva));

        // Validaciones de estado
        if (reserva.getEstado().equalsIgnoreCase("Cancelada")) {
            throw new RuntimeException("La reserva ya está cancelada.");
        }

        if (reserva.getEstado().equalsIgnoreCase("Confirmada")) {
            throw new RuntimeException("No se puede cancelar una reserva ya confirmada.");
        }

        // Liberar habitaciones reales
        for (Integer nro : reserva.getHabitaciones()) {
            habitacionService.cambiarEstado(nro, "Disponible");
        }

        // Cambiar estado de la reserva REAL
        reserva.setEstado("Cancelada");
        reservaDAO.update(reserva);

        // Devolver DTO COMPLETO reconstruido
        return mapearReservaADTO(reserva);
    }



}
