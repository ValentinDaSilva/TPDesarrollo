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

public class MapearADTO {

    public static DireccionDTO mapearDireccion(Direccion direccion) {
        if (direccion == null) return null;
        return new DireccionDTO(
                direccion.getCalle(),
                direccion.getNumero(),
                direccion.getDepartamento(),
                direccion.getPiso(),
                direccion.getCodigoPostal(),
                direccion.getLocalidad(),
                direccion.getProvincia(),
                direccion.getPais()
        );
    }

    public static HuespedDTO mapearHuesped(Huesped h) {
        if (h == null) return null;
        HuespedDTO dto = new HuespedDTO();
        dto.setNombre(h.getNombre());
        dto.setApellido(h.getApellido());
        dto.setTipoDocumento(h.getTipoDocumento());
        dto.setnumeroDocumento(h.getNumeroDocumento());
        dto.setOcupacion(h.getOcupacion());
        dto.setNacionalidad(h.getNacionalidad());
        dto.setCuit(h.getCuit());
        dto.setEmail(h.getEmail());
        dto.setCondicionIVA(h.getPosicionIVA());
        dto.setDireccion(mapearDireccion(h.getDireccion()));
        return dto;
    }

    public static TitularDTO mapearTitular(Titular t) {
        if (t == null) return null;
        return new TitularDTO(t.getNombre(), t.getApellido(), t.getTelefono());
    }

    public static HabitacionDTO mapearHabitacion(Habitacion h) {
        if (h == null) return null;
        HabitacionDTO dto = new HabitacionDTO();
        dto.setNumero(h.getNumero());
        dto.setTipo(h.getTipo());
        dto.setCategoria(h.getCategoria());
        dto.setCostoPorNoche(h.getCostoPorNoche());
        dto.setEstado(h.getEstado());
        return dto;
    }

    public static MedioDePagoDTO mapearMedioDePago(MedioDePago m) {
        if (m == null) return null;

        if (m instanceof Tarjeta t)
            return new TarjetaDTO(t.getTipoTarjeta(), t.getNumeroTarjeta(), t.getMonto());

        if (m instanceof Cheque c)
            return new ChequeDTO(c.getNumero(), c.getMonto(), c.getFechaVencimiento());

        if (m instanceof MonedaExtranjera me)
            return new MonedaExtranjeraDTO(me.getTipoMoneda(), me.getMontoExtranjero(), me.getCotizacion(), me.getMonto());

        if (m instanceof Efectivo e)
            return new EfectivoDTO(e.getMonto());

        throw new RuntimeException("Tipo de medio desconocido: " + m.getClass());
    }

    public static ResponsableDePagoDTO mapearResponsableDePago(ResponsableDePago r) {
        if (r == null) return null;

        if (r instanceof ResponsableHuesped h) {
            ResponsableHuespedDTO dto = new ResponsableHuespedDTO();
            dto.setApellido(h.getApellido());
            dto.setNombres(h.getNombres());
            dto.setDocumento(h.getDocumento());
            return dto;
        }

        if (r instanceof ResponsableJuridico j) {
            ResponsableJuridicoDTO dto = new ResponsableJuridicoDTO();
            dto.setRazonSocial(j.getRazonSocial());
            dto.setCuit(j.getCuit());
            return dto;
        }

        throw new RuntimeException("Tipo de responsable desconocido: " + r.getClass());
    }

    public static PagoDTO mapearPago(Pago p) {
        if (p == null) return null;
        return new PagoDTO(
                p.getId(),
                p.getFecha(),
                p.getHora(),
                p.getMontoTotal(),
                mapearMedioDePago(p.getMedioDePago())
        );
    }

    public static List<PagoDTO> mapearPagos(List<Pago> pagos) {
        if (pagos == null) return new ArrayList<>();
        return pagos.stream().map(MapearADTO::mapearPago).collect(Collectors.toList());
    }

    public static ReservaDTO mapearReserva(Reserva r) {
        if (r == null) return null;
        ReservaDTO dto = new ReservaDTO();
        dto.setId(r.getId());
        dto.setFechaInicio(fechaToString(r.getFechaInicio()));
        dto.setFechaFin(fechaToString(r.getFechaFin()));
        dto.setEstado(r.getEstado());
        dto.setTitular(mapearTitular(r.getTitular()));
        dto.setHabitaciones(new ArrayList<>());
        return dto;
    }

    public static EstadiaDTO mapearEstadia(Estadia e) {
        if (e == null) return null;
        EstadiaDTO dto = new EstadiaDTO();
        dto.setId(e.getId());
        dto.setFechaCheckIn(fechaToString(e.getFechaCheckIn()));
        dto.setFechaCheckOut(fechaToString(e.getFechaCheckOut()));
        dto.setEstado(e.getEstado());
        dto.setTitular(mapearHuesped(e.getTitular()));
        if (e.getAcompaniantes() != null) {
            dto.setAcompaniantes(
                    e.getAcompaniantes().stream()
                            .map(MapearADTO::mapearHuesped)
                            .collect(Collectors.toList())
            );
        }
        dto.setReserva(null);
        dto.setConsumos(new ArrayList<>());
        return dto;
    }

    public static FacturaDTO mapearFactura(Factura f) {
        if (f == null) return null;
        return new FacturaDTO(
                f.getId(),
                f.getHora(),
                f.getFecha(),
                f.getTipo(),
                f.getEstado(),
                mapearResponsableDePago(f.getResponsableDePago()),
                mapearMedioDePago(f.getMedioDePago()),
                mapearEstadia(f.getEstadia()),
                mapearPagos(f.getPagos()),
                f.getTotal(),
                f.getIva(),
                f.getDetalle(),
                f.getHoraSalida()
        );
    }

    public static String fechaToString(LocalDate fecha) {
        return fecha != null ? fecha.toString() : null;
    }
    public static NotaDeCreditoDTO mapearNotaDeCredito(NotaDeCredito dominio) {
        if (dominio == null) return null;

        return new NotaDeCreditoDTO(
            dominio.getIdNota(),
            dominio.getFecha(),
            mapearResponsableDePago(dominio.getResponsable()),
            dominio.getFacturas() != null
                ? dominio.getFacturas()
                    .stream()
                    .map(MapearADTO::mapearFactura)
                    .collect(Collectors.toList())
                : new ArrayList<>(),
            dominio.getTipo()
        );
    }
}
