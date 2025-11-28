// [src/main/java/com/hotel/hotel_backend/service/Mapeo/MapearADTO.java]
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

    // ==========================================================
    //                      DIRECCION
    // ==========================================================
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

    // ==========================================================
    //                      HUESPED
    // ==========================================================
    public static HuespedDTO mapearHuesped(Huesped h) {

    if (h == null) return null;

    HuespedDTO dto = new HuespedDTO();

    dto.setNumeroDocumento(h.getNumeroDocumento());
    dto.setApellido(h.getApellido());
    dto.setNombre(h.getNombre());
    dto.setTipoDocumento(h.getTipoDocumento());
    dto.setCuit(h.getCuit());
    dto.setEmail(h.getEmail());
    dto.setOcupacion(h.getOcupacion());
    dto.setNacionalidad(h.getNacionalidad());
    dto.setTelefono(h.getTelefono());

    Direccion dir = h.getDireccion();
    if (dir != null) {
        DireccionDTO d = new DireccionDTO(
                dir.getCalle(),
                dir.getNumero(),
                dir.getDepartamento(),
                dir.getPiso(),
                dir.getCodigoPostal(),
                dir.getLocalidad(),
                dir.getProvincia(),
                dir.getPais()
        );
        dto.setDireccion(d);
    }

    return dto;
}


    // ==========================================================
    //                      PERSONA
    // ==========================================================
    public static PersonaDTO mapearpersona(Persona t) {
        if (t == null) return null;
        return new PersonaDTO(
                t.getNombre(),
                t.getApellido(),
                t.getTelefono()
        );
    }

    // ==========================================================
    //                      HABITACION
    // ==========================================================
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

    // ==========================================================
    //               MEDIO DE PAGO → DTO
    // ==========================================================
    public static MedioDePagoDTO mapearMedioDePago(MedioDePago m) {
        if (m == null) return null;

        if (m instanceof Tarjeta t)
            return new TarjetaDTO(t.getTipoTarjeta(), t.getNumeroTarjeta(), t.getMonto());

        if (m instanceof Cheque c)
            return new ChequeDTO(c.getNumero(), c.getMonto(), c.getFechaVencimiento());

        if (m instanceof MonedaExtranjera me)
            return new MonedaExtranjeraDTO(
                    me.getTipoMoneda(),
                    me.getMontoExtranjero(),
                    me.getCotizacion(),
                    me.getMonto()
            );

        if (m instanceof Efectivo e)
            return new EfectivoDTO(e.getMonto());

        throw new RuntimeException("Tipo de medio de pago desconocido: " + m.getClass());
    }

    // ==========================================================
    //           RESPONSABLE DE PAGO → DTO
    // ==========================================================
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

    // ==========================================================
    //                      PAGO → DTO
    // ==========================================================
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

    // ==========================================================
    //                    RESERVA → DTO
    // ==========================================================
    public static ReservaDTO mapearReserva(Reserva r) {

        if (r == null) return null;

        ReservaDTO dto = new ReservaDTO();

        dto.setId(r.getId());
        dto.setFechaInicio(r.getFechaInicio() != null ? r.getFechaInicio().toString() : null);
        dto.setFechaFin(r.getFechaFin() != null ? r.getFechaFin().toString() : null);
        dto.setEstado(r.getEstado());

        // Titular
        if (r.getTitular() != null) {
            dto.setTitular(mapearpersona(r.getTitular()));
        }

        // Habitaciones
        if (r.getHabitaciones() != null) {
            dto.setHabitaciones(
                    r.getHabitaciones().stream()
                            .map(MapearADTO::mapearHabitacion)
                            .toList()
            );
        }

        return dto;
    }


    // ==========================================================
    //                    ESTADIA → DTO
    // ==========================================================
    // src/main/java/com/hotel/hotel_backend/service/Mapeo/MapearADTO.java

public static EstadiaDTO mapearEstadia(Estadia e) {

    if (e == null) return null;

    EstadiaDTO dto = new EstadiaDTO();

    dto.setId(e.getId());

    dto.setFechaCheckIn(e.getFechaCheckIn() != null ? e.getFechaCheckIn().toString() : null);
    dto.setHoraCheckIn(e.getHoraCheckIn() != null ? e.getHoraCheckIn().toString() : null);

    dto.setFechaCheckOut(e.getFechaCheckOut() != null ? e.getFechaCheckOut().toString() : null);
    dto.setHoraCheckOut(e.getHoraCheckOut() != null ? e.getHoraCheckOut().toString() : null);

    dto.setEstado(e.getEstado());

    dto.setReserva(mapearReserva(e.getReserva()));

    if (e.getTitular() != null)
        dto.setTitular(mapearHuesped(e.getTitular()));

    if (e.getAcompaniantes() != null)
        dto.setAcompaniantes(
                e.getAcompaniantes().stream()
                        .map(MapearADTO::mapearHuesped)
                        .toList()
        );

    return dto;
}


    // ==========================================================
    //                    FACTURA → DTO
    // ==========================================================
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

    // ==========================================================
    //                      FECHAS
    // ==========================================================
    public static String fechaToString(LocalDate fecha) {
        return fecha != null ? fecha.toString() : null;
    }

    // ==========================================================
    //                 NOTA DE CRÉDITO → DTO
    // ==========================================================
    public static NotaDeCreditoDTO mapearNotaDeCredito(NotaDeCredito dominio) {
        if (dominio == null) return null;

        return new NotaDeCreditoDTO(
                dominio.getIdNota(),
                dominio.getFecha(),
                mapearResponsableDePago(dominio.getResponsable()),
                dominio.getFacturas() != null
                        ? dominio.getFacturas().stream()
                            .map(MapearADTO::mapearFactura)
                            .collect(Collectors.toList())
                        : new ArrayList<>(),
                dominio.getTipo()
        );
    }
}
