package com.hotel.hotel_backend.service;

import com.hotel.hotel_backend.dao.HabitacionDAO;
import com.hotel.hotel_backend.dao.ReservaDAO;
import com.hotel.hotel_backend.domain.Habitacion;
import com.hotel.hotel_backend.domain.Persona;
import com.hotel.hotel_backend.domain.Reserva;
import com.hotel.hotel_backend.dto.HabitacionDTO;
import com.hotel.hotel_backend.dto.ReservaDTO;
import com.hotel.hotel_backend.dto.PersonaDTO;
import com.hotel.hotel_backend.service.Mapeo.MapearADTO;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservaService {

    private final ReservaDAO reservaDAO;
    private final HabitacionDAO habitacionDAO;

    public ReservaService(ReservaDAO reservaDAO, HabitacionDAO habitacionDAO) {
        this.reservaDAO = reservaDAO;
        this.habitacionDAO = habitacionDAO;
    }

    // =========================================================
    // CREAR RESERVA
    // =========================================================
    public ReservaDTO reservarHabitacion(ReservaDTO dto) {

        if (dto == null)
            throw new RuntimeException("La reserva no puede estar vacía.");

        LocalDate inicio = parse(dto.getFechaInicio());
        LocalDate fin = parse(dto.getFechaFin());

        if (fin.isBefore(inicio))
            throw new RuntimeException("La fecha de fin no puede ser anterior a la de inicio.");

        PersonaDTO t = dto.getTitular();
        if (t == null)
            throw new RuntimeException("Debe enviar titular de la reserva.");

        Persona titular = new Persona(t.getNombre(), t.getApellido(), t.getTelefono());

        if (dto.getHabitaciones() == null || dto.getHabitaciones().isEmpty())
            throw new RuntimeException("Debe seleccionar al menos una habitación.");

        // buscar habitaciones
        List<Habitacion> habitacionesReales =
                dto.getHabitaciones().stream()
                        .map(HabitacionDTO::getNumero)
                        .map(n -> habitacionDAO.findByNumero(n))
                        .toList();

        // validar solapamiento
        for (Habitacion h : habitacionesReales) {
            if (reservaDAO.existeSolapamiento(h.getNumero(), inicio, fin)) {
                throw new RuntimeException("La habitación Nº " + h.getNumero()
                        + " ya está reservada en ese rango de fechas.");
            }
        }

        Reserva r = new Reserva();
        r.setFechaInicio(inicio);
        r.setFechaFin(fin);
        r.setTitular(titular);
        r.setHabitaciones(habitacionesReales);

        // ============================
        //  NUEVO: estado inicial = PENDIENTE
        // ============================
        r.setEstado("Pendiente");

        reservaDAO.save(r);

        return MapearADTO.mapearReserva(r);
    }

    // =========================================================
    // LISTAR ENTRE FECHAS
    // =========================================================
    public List<ReservaDTO> listarReservasEntre(String inicio, String fin) {

        LocalDate f1 = parse(inicio);
        LocalDate f2 = parse(fin);

        if (f2.isBefore(f1))
            throw new RuntimeException("La fecha final no puede ser anterior al inicio.");

        return reservaDAO.buscarEntreFechas(f1, f2)
                .stream()
                .map(MapearADTO::mapearReserva)
                .toList();
    }

    // =========================================================
    // CANCELAR RESERVA
    // =========================================================
    public ReservaDTO cancelarReserva(ReservaDTO dto) {

        if (dto.getId() == null)
            throw new RuntimeException("Debe enviar el ID de la reserva.");

        Reserva reserva = reservaDAO.findById(dto.getId())
                .orElseThrow(() ->
                        new RuntimeException("No existe la reserva con id " + dto.getId()));

        if (reserva.getEstado().equalsIgnoreCase("Cancelada"))
            throw new RuntimeException("La reserva ya está cancelada.");

        reserva.setEstado("Cancelada");
        reservaDAO.save(reserva);

        return MapearADTO.mapearReserva(reserva);
    }

    // =========================================================
    // UTILIDADES
    // =========================================================
    private LocalDate parse(String s) {
        if (s == null || s.isEmpty())
            throw new RuntimeException("Fecha obligatoria.");
        return LocalDate.parse(s);
    }
}
