package com.hotel.hotel_backend.service.Mapeo;

import com.hotel.hotel_backend.domain.*;
import com.hotel.hotel_backend.domain.MedioDePago.*;
import com.hotel.hotel_backend.domain.ResponsableDePago.*;
import com.hotel.hotel_backend.dto.*;
import com.hotel.hotel_backend.dto.MedioDePago.*;
import com.hotel.hotel_backend.dto.ResponsableDePago.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MapearADominio {

    public static Direccion mapearDireccion(DireccionDTO dto) {
        if (dto == null) return null;
        return new Direccion(
                dto.getCalle(),
                dto.getNumero(),
                dto.getDepartamento(),
                dto.getPiso(),
                dto.getCodigoPostal(),
                dto.getLocalidad(),
                dto.getProvincia(),
                dto.getPais()
        );
    }

    public static Huesped mapearHuesped(HuespedDTO dto) {
        if (dto == null) return null;
        return new Huesped(
                dto.getApellido(),
                dto.getNombre(),
                dto.getTipoDocumento(),
                dto.getnumeroDocumento(),
                dto.getCuit(),
                dto.getCondicionIVA(),
                mapearDireccion(dto.getDireccion()),
                dto.getEmail(),
                dto.getOcupacion(),
                dto.getNacionalidad()
        );
    }

    public static Titular mapearTitular(TitularDTO dto) {
        if (dto == null) return null;
        return new Titular(
                dto.getNombre(),
                dto.getApellido(),
                dto.getTelefono()
        );
    }

    public static Habitacion mapearHabitacion(HabitacionDTO dto) {
        if (dto == null) return null;
        return new Habitacion(
                dto.getNumero(),
                dto.getTipo(),
                dto.getCategoria(),
                dto.getCostoPorNoche(),
                dto.getEstado()
        );
    }

    public static MedioDePago mapearMedioDePago(MedioDePagoDTO dto) {
        if (dto == null) return null;

        if (dto instanceof TarjetaDTO t)
            return new Tarjeta(t.getTipoTarjeta(), t.getNumeroTarjeta(), t.getMonto());

        if (dto instanceof ChequeDTO c)
            return new Cheque(c.getNumero(), c.getMonto(), c.getFechaVencimiento());

        if (dto instanceof MonedaExtranjeraDTO m)
            return new MonedaExtranjera(m.getTipoMoneda(), m.getMontoExtranjero(), m.getCotizacion(), m.getMonto());

        if (dto instanceof EfectivoDTO e)
            return new Efectivo(e.getMonto());

        throw new RuntimeException("Tipo de medio de pago desconocido: " + dto.getClass());
    }

    public static ResponsableDePago mapearResponsableDePago(ResponsableDePagoDTO dto) {
        if (dto == null) return null;

        if (dto instanceof ResponsableHuespedDTO h) {
            ResponsableHuesped r = new ResponsableHuesped();
            r.setApellido(h.getApellido());
            r.setNombres(h.getNombres());
            r.setDocumento(h.getDocumento());
            return r;
        }

        if (dto instanceof ResponsableJuridicoDTO j) {
            ResponsableJuridico r = new ResponsableJuridico();
            r.setRazonSocial(j.getRazonSocial());
            r.setCuit(j.getCuit());
            return r;
        }

        throw new RuntimeException("Tipo de responsable desconocido: " + dto.getClass());
    }

    public static Pago mapearPago(PagoDTO dto) {
        if (dto == null) return null;
        return new Pago(
                dto.getId(),
                dto.getFecha(),
                dto.getHora(),
                dto.getMontoTotal(),
                mapearMedioDePago(dto.getMedioDePago())
        );
    }

    public static List<Pago> mapearPagos(List<PagoDTO> dto) {
        if (dto == null) return new ArrayList<>();
        return dto.stream().map(MapearADominio::mapearPago).collect(Collectors.toList());
    }

    public static Reserva mapearReserva(ReservaDTO dto) {
        if (dto == null) return null;

        LocalDate inicio = stringToFecha(dto.getFechaInicio());
        LocalDate fin = stringToFecha(dto.getFechaFin());

        List<Integer> habitaciones = new ArrayList<>();
        if (dto.getHabitaciones() != null) {
            habitaciones = dto.getHabitaciones().stream()
                    .map(HabitacionDTO::getNumero)
                    .collect(Collectors.toList());
        }

        return new Reserva(
                dto.getId() != null ? dto.getId() : 0,
                inicio,
                fin,
                mapearTitular(dto.getTitular()),
                habitaciones,
                dto.getEstado()
        );
    }

    public static Estadia mapearEstadia(EstadiaDTO dto) {
        if (dto == null) return null;

        LocalDate in = stringToFecha(dto.getFechaCheckIn());
        LocalDate out = stringToFecha(dto.getFechaCheckOut());

        List<Huesped> acomp = new ArrayList<>();
        if (dto.getAcompaniantes() != null) {
            acomp = dto.getAcompaniantes().stream()
                    .map(MapearADominio::mapearHuesped)
                    .collect(Collectors.toList());
        }

        int idReserva = dto.getReserva() != null && dto.getReserva().getId() != null
                ? dto.getReserva().getId()
                : 0;

        return new Estadia(
                dto.getId() != null ? dto.getId() : 0,
                in,
                out,
                dto.getEstado(),
                idReserva,
                mapearHuesped(dto.getTitular()),
                acomp
        );
    }

    public static Factura mapearFactura(FacturaDTO dto) {
        if (dto == null) return null;

        return new Factura(
                dto.getId(),
                dto.getHora(),
                dto.getFecha(),
                dto.getTipo(),
                dto.getEstado(),
                mapearResponsableDePago(dto.getResponsableDePago()),
                mapearMedioDePago(dto.getMedioDePago()),
                mapearEstadia(dto.getEstadia()),
                mapearPagos(dto.getPagos()),
                dto.getTotal(),
                dto.getIva(),
                dto.getDetalle(),
                dto.getHoraSalida()
        );
    }

    public static LocalDate stringToFecha(String f) {
        if (f == null || f.isEmpty()) return null;
        return LocalDate.parse(f);
    }

    public static NotaDeCredito mapearNotaDeCredito(NotaDeCreditoDTO dto) {
        if (dto == null) return null;

        return new NotaDeCredito(
            dto.getIdNota(),
            dto.getFecha(),
            mapearResponsableDePago(dto.getResponsable()),
            dto.getFacturas() != null
                ? dto.getFacturas()
                    .stream()
                    .map(MapearADominio::mapearFactura)
                    .collect(Collectors.toList())
                : new ArrayList<>(),
            dto.getTipo()
        );
    }

    

}
