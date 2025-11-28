package com.hotel.hotel_backend.service.Mapeo;

import com.hotel.hotel_backend.domain.*;
import com.hotel.hotel_backend.domain.MedioDePago.*;
import com.hotel.hotel_backend.domain.ResponsableDePago.*;

import com.hotel.hotel_backend.dto.*;
import com.hotel.hotel_backend.dto.MedioDePago.*;
import com.hotel.hotel_backend.dto.ResponsableDePago.*;

public class MapearADominio {

    // =====================================================
    //                    DIRECCIÓN
    // =====================================================
    public static Direccion mapearDireccion(DireccionDTO dto) {
        if (dto == null) return null;

        Direccion d = new Direccion();
        d.setCalle(dto.getCalle());
        d.setNumero(dto.getNumero());
        d.setDepartamento(dto.getDepartamento());
        d.setPiso(dto.getPiso());
        d.setCodigoPostal(dto.getCodigoPostal());
        d.setLocalidad(dto.getLocalidad());
        d.setProvincia(dto.getProvincia());
        d.setPais(dto.getPais());
        return d;
    }

    // =====================================================
    //                    HUESPED
    // =====================================================
    public static Huesped mapearHuesped(HuespedDTO dto) {

        if (dto == null) return null;

        Huesped h = new Huesped();

        h.setNumeroDocumento(dto.getNumeroDocumento());
        h.setApellido(dto.getApellido());
        h.setNombre(dto.getNombre());
        h.setTipoDocumento(dto.getTipoDocumento());
        h.setCuit(dto.getCuit());
        h.setEmail(dto.getEmail());
        h.setOcupacion(dto.getOcupacion());
        h.setNacionalidad(dto.getNacionalidad());
        h.setTelefono(dto.getTelefono());
        h.setFechaNacimiento(dto.getFechaNacimiento());

        // Direccion anidada
        if (dto.getDireccion() != null) {
            Direccion d = new Direccion();
            d.setCalle(dto.getDireccion().getCalle());
            d.setNumero(dto.getDireccion().getNumero());
            d.setDepartamento(dto.getDireccion().getDepartamento());
            d.setPiso(dto.getDireccion().getPiso());
            d.setCodigoPostal(dto.getDireccion().getCodigoPostal());
            d.setLocalidad(dto.getDireccion().getLocalidad());
            d.setProvincia(dto.getDireccion().getProvincia());
            d.setPais(dto.getDireccion().getPais());
            h.setDireccion(d);
        }

        return h;
    }


    // =====================================================
    //                    FACTURA
    // =====================================================
    public static Factura mapearFactura(FacturaDTO dto) {
        if (dto == null) return null;

        Factura f = new Factura();
        f.setId(dto.getId());
        f.setFecha(dto.getFecha());
        f.setHora(dto.getHora());
        f.setEstado(dto.getEstado());
        f.setTipo(dto.getTipo());
        f.setTotal(dto.getTotal());
        f.setIva(dto.getIva());
        f.setDetalle(dto.getDetalle());
        f.setHoraSalida(dto.getHoraSalida());
        return f;
    }

    // =====================================================
    //                      PAGO
    // =====================================================
    public static Pago mapearPago(PagoDTO dto) {
        if (dto == null) return null;

        Pago p = new Pago();
        p.setId(dto.getId());
        p.setFecha(dto.getFecha());
        p.setHora(dto.getHora());
        p.setMontoTotal(dto.getMontoTotal());  // EXISTE EN DTO
        p.setMedioDePago(mapearMedioDePago(dto.getMedioDePago()));

        return p;
    }

    // =====================================================
    //            RESPONSABLE DE PAGO (polimórfico)
    // =====================================================
    public static ResponsableDePago mapearResponsableDePago(ResponsableDePagoDTO dto) {
        if (dto == null) return null;

        if (dto instanceof ResponsableHuespedDTO rh) {
            ResponsableHuesped r = new ResponsableHuesped();
            r.setApellido(rh.getApellido());
            r.setNombres(rh.getNombres());
            r.setDocumento(rh.getDocumento());
            return r;
        }

        if (dto instanceof ResponsableJuridicoDTO rj) {
            ResponsableJuridico r = new ResponsableJuridico();
            r.setRazonSocial(rj.getRazonSocial());
            r.setCuit(rj.getCuit());
            return r;
        }

        throw new RuntimeException("Tipo de ResponsableDePagoDTO inválido: " + dto.getClass());
    }

    // =====================================================
    //            MEDIO DE PAGO (polimórfico)
    // =====================================================
    public static MedioDePago mapearMedioDePago(MedioDePagoDTO dto) {
        if (dto == null) return null;

        // ---------- EFECTIVO ----------
        if (dto instanceof EfectivoDTO ef) {
            return new Efectivo(ef.getMonto());
        }

        // ---------- CHEQUE ----------
        if (dto instanceof ChequeDTO c) {
            return new Cheque(
                    c.getNumero(),
                    c.getMonto(),
                    c.getFechaVencimiento()
            );
        }

        // ---------- TARJETA ----------
        if (dto instanceof TarjetaDTO t) {
            return new Tarjeta(
                    t.getTipoTarjeta(),
                    t.getNumeroTarjeta(),
                    t.getMonto()
            );
        }

        // ---------- MONEDA EXTRANJERA ----------
        if (dto instanceof MonedaExtranjeraDTO m) {
            return new MonedaExtranjera(
                    m.getTipoMoneda(),
                    m.getMontoExtranjero(),
                    m.getCotizacion(),
                    m.getMonto()
            );
        }

        throw new RuntimeException("Tipo de MedioDePagoDTO inválido: " + dto.getClass());
    }

    // =====================================================
    //                 NOTA DE CRÉDITO
    // =====================================================
    public static NotaDeCredito mapearNotaDeCredito(NotaDeCreditoDTO dto) {
        if (dto == null) return null;

        NotaDeCredito nc = new NotaDeCredito();
        nc.setIdNota(dto.getIdNota());
        nc.setFecha(dto.getFecha());
        nc.setResponsable(mapearResponsableDePago(dto.getResponsable()));
        nc.setTipo(dto.getTipo());
        // La lista de facturas se reemplaza en FacturaService
        return nc;
    }
}
