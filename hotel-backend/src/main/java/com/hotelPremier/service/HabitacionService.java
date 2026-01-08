package com.hotelPremier.service;

import com.hotelPremier.classes.DTO.EstadiaDTO;
import com.hotelPremier.classes.DTO.HabitacionDTO;
import com.hotelPremier.classes.DTO.ReservaDTO;
import com.hotelPremier.classes.Dominio.Estadia;
import com.hotelPremier.classes.Dominio.Habitacion;
import com.hotelPremier.classes.Dominio.Huesped;
import com.hotelPremier.classes.Dominio.Reserva;
import com.hotelPremier.repository.HabitacionRepository;
import com.hotelPremier.classes.mapper.ClassMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class HabitacionService {

    @Autowired
    private HabitacionRepository habitacionRepository;

    @Autowired
    private ClassMapper mapper;

    /**
     * Obtiene habitaciones filtradas por tipo.
     */
    public List<HabitacionDTO> getHabitaciones(String tipoHabitacion) {
        return mapper.toDTOsHabitacion(
                habitacionRepository.buscarPorTipoHabitacion(tipoHabitacion)
        );
    }

    /**
     * Obtiene una habitación por número.
     */
    public HabitacionDTO getHabitacion(Integer numero) {
        return mapper.toDTOHab(habitacionRepository.findByNumero(numero));
    }

    /**
     * Busca habitaciones disponibles en un rango de fechas.
     */
    public List<HabitacionDTO> buscarListaHabitaciones(
            String tipo,
            LocalDate fechaDesde,
            LocalDate fechaHasta
    ) {
        return mapper.toDTOsHabitacion(
                habitacionRepository.buscarListaHabitaciones(tipo, fechaDesde, fechaHasta)
        );
    }

    /**
     * Obtiene habitaciones con reservas y estadías en un rango de fechas.
     */
    public List<HabitacionDTO> obtenerHabitacionesConDetalle(
            String tipo,
            LocalDate fechaDesde,
            LocalDate fechaHasta
    ) {

        List<Habitacion> habitaciones =
                habitacionRepository.buscarPorTipoHabitacion(tipo);

        List<HabitacionDTO> resultado = new ArrayList<>();

        for (Habitacion h : habitaciones) {

            // Reservas que se solapan con el rango
            List<Reserva> reservas = h.getListaReservas().stream()
                    .filter(r ->
                            !r.getFecha_desde().isAfter(fechaHasta) &&
                            !r.getFecha_hasta().isBefore(fechaDesde)
                    )
                    .toList();

            // Estadías que se solapan con el rango
            List<Estadia> estadias = h.getListaEstadia().stream()
                    .filter(e ->
                            !e.getCheckin().isAfter(fechaHasta) &&
                            !e.getCheckout().isBefore(fechaDesde)
                    )
                    .toList();

            List<ReservaDTO> reservasDTO = mapper.toDtosReserva(reservas);
            List<EstadiaDTO> estadiasDTO = mapper.toDTOsEstadia(estadias);

            resultado.add(new HabitacionDTO(h, reservasDTO, estadiasDTO));
        }

        return resultado;
    }

    /**
     * Obtiene los huéspedes asociados a una habitación.
     */
    public List<Huesped> obtenerHuespedesPorHabitacion(Integer nroHabitacion) {

        Habitacion habitacion = habitacionRepository.findById(nroHabitacion)
                .orElse(null);

        if (habitacion == null || habitacion.getListaEstadia() == null) {
            return List.of();
        }

        return habitacion.getListaEstadia().stream()
                .filter(e -> e.getListahuesped() != null)
                .flatMap(e -> e.getListahuesped().stream())
                .distinct()
                .toList();
    }

    /**
     * Obtiene todas las habitaciones.
     */
    public List<HabitacionDTO> getHabitaciones() {
        return mapper.toDTOsHabitacion(habitacionRepository.findAll());
    }
}
