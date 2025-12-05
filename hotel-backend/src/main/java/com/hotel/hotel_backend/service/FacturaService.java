// src/main/java/com/hotel/hotel_backend/service/FacturaService.java
package com.hotel.hotel_backend.service;

import java.time.LocalDate;
import java.util.List;

import com.hotel.hotel_backend.dao.FacturaDAO;
import com.hotel.hotel_backend.dao.NotaDeCreditoDAO;
import com.hotel.hotel_backend.dao.EstadiaDAO;

import com.hotel.hotel_backend.domain.Factura;
import com.hotel.hotel_backend.domain.NotaDeCredito;
import com.hotel.hotel_backend.domain.Estadia;
import com.hotel.hotel_backend.domain.Pago;
import com.hotel.hotel_backend.domain.ResponsableDePago.ResponsableDePago;

import com.hotel.hotel_backend.dto.FacturaDTO;
import com.hotel.hotel_backend.dto.NotaDeCreditoDTO;
import com.hotel.hotel_backend.dto.PagoDTO;

import com.hotel.hotel_backend.service.Mapeo.MapearADTO;
import com.hotel.hotel_backend.service.Mapeo.MapearADominio;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class FacturaService {

    private final FacturaDAO facturaDAO;
    private final NotaDeCreditoDAO notaDAO;
    private final EstadiaDAO estadiaDAO;

    @PersistenceContext
    private EntityManager em;

    public FacturaService(FacturaDAO facturaDAO,
                          NotaDeCreditoDAO notaDAO,
                          EstadiaDAO estadiaDAO) {

        this.facturaDAO = facturaDAO;
        this.notaDAO = notaDAO;
        this.estadiaDAO = estadiaDAO;
    }

    // ==================================================
    //                CREAR FACTURA
    // ==================================================
    public FacturaDTO generarFactura(FacturaDTO dto) {

        if (dto == null)
            throw new RuntimeException("La factura no puede ser nula.");

        if (dto.getEstadia() == null || dto.getEstadia().getId() == null)
            throw new RuntimeException("Debe enviar ID de la estadía.");

        Estadia estadia = estadiaDAO.findById(dto.getEstadia().getId())
                .orElseThrow(() -> new RuntimeException(
                        "No existe la estadía con ID " + dto.getEstadia().getId()
                ));

        // Opcional pero recomendable: solo facturar estadías finalizadas
        if (!"Finalizada".equalsIgnoreCase(estadia.getEstado())) {
            throw new RuntimeException("Solo se puede facturar una estadía finalizada.");
        }

        // Mapeo base
        Factura factura = MapearADominio.mapearFactura(dto);

        // RESPONSABLE
        if (dto.getResponsableDePago() != null) {
            ResponsableDePago responsable = MapearADominio.mapearResponsableDePago(dto.getResponsableDePago());
            em.persist(responsable);
            factura.setResponsableDePago(responsable);
        }



        // PAGOS (si vienen)
        if (dto.getPagos() != null && !dto.getPagos().isEmpty()) {
            List<Pago> pagos = dto.getPagos().stream()
                    .map(MapearADominio::mapearPago)
                    .toList();

            pagos.forEach(p -> p.setFactura(factura));
            factura.setPagos(pagos);
        }

        factura.setEstadia(estadia);
        facturaDAO.save(factura);

        return MapearADTO.mapearFactura(factura);
    }

    // ==================================================
    //              ACTUALIZAR FACTURA
    // ==================================================
    public FacturaDTO actualizarFactura(FacturaDTO dto) {

        if (dto == null)
            throw new RuntimeException("La factura no puede ser nula.");

        if (dto.getId() == 0)
            throw new RuntimeException("Debe enviar ID de la factura.");

        Factura original = facturaDAO.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException(
                        "No existe la factura con ID " + dto.getId()
                ));

        if (!original.getEstado().equalsIgnoreCase("Pendiente")) {
            throw new RuntimeException("Solo se pueden modificar facturas pendientes.");
        }

        // 1) Actualizar campos primitivos
        original.setHora(dto.getHora());
        original.setFecha(dto.getFecha());
        original.setTipo(dto.getTipo());
        original.setEstado(dto.getEstado());
        original.setTotal(dto.getTotal());
        original.setIva(dto.getIva());
        original.setDetalle(dto.getDetalle());
        original.setHoraSalida(dto.getHoraSalida());

        // 2) Responsable
        if (dto.getResponsableDePago() != null) {
            ResponsableDePago resp = MapearADominio.mapearResponsableDePago(dto.getResponsableDePago());
            em.persist(resp);
            original.setResponsableDePago(resp);
        }


        // 4) Pagos
        if (dto.getPagos() != null) {
            List<Pago> pagos = dto.getPagos().stream()
                    .map(MapearADominio::mapearPago)
                    .toList();

            pagos.forEach(p -> p.setFactura(original));
            original.setPagos(pagos);
        }

        facturaDAO.save(original);

        return MapearADTO.mapearFactura(original);
    }

    // ==================================================
    //              OBTENER FACTURA POR ID
    // ==================================================
    public FacturaDTO obtenerFacturaPorId(int id) {

        Factura factura = facturaDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada con ID " + id));

        return MapearADTO.mapearFactura(factura);
    }

    // ==================================================
    //                LISTAR TODAS
    // ==================================================
    public List<FacturaDTO> listarTodas() {
        return facturaDAO.findAll().stream()
                .map(MapearADTO::mapearFactura)
                .toList();
    }

    // ==================================================
    //       PENDIENTES POR HABITACIÓN
    // ==================================================
    public List<FacturaDTO> obtenerPendientesPorHabitacion(int numeroHabitacion) {
        return facturaDAO.findPendientesByHabitacion(numeroHabitacion)
                .stream()
                .map(MapearADTO::mapearFactura)
                .toList();
    }

    // ==================================================
    //                PAGOS ENTRE FECHAS
    // ==================================================
    public List<PagoDTO> buscarPagoesEntreFechas(LocalDate desde, LocalDate hasta) {

        return facturaDAO.findPagosEntreFechas(desde, hasta)
                .stream()
                .map(MapearADTO::mapearPago)
                .toList();
    }

    // ==================================================
    //                CHEQUES ENTRE FECHAS
    // ==================================================
    public List<PagoDTO> buscarChequesEntreFechas(LocalDate desde, LocalDate hasta) {

        return facturaDAO.findChequesEntreFechas(desde, hasta)
                .stream()
                .map(MapearADTO::mapearPago)
                .toList();
    }

    // ==================================================
    //             CREAR NOTA DE CRÉDITO
    // ==================================================
    public NotaDeCreditoDTO crearNotaDeCredito(NotaDeCreditoDTO dto) {

        if (dto == null)
            throw new RuntimeException("La nota de crédito no puede ser nula.");

        NotaDeCredito nota = MapearADominio.mapearNotaDeCredito(dto);

        // Reemplazar facturas de referencia por facturas REALES
        List<Factura> reales = dto.getFacturas().stream()
                .map(f -> facturaDAO.findById(f.getId())
                        .orElseThrow(() ->
                                new RuntimeException("Factura no encontrada con ID " + f.getId())))
                .toList();

        // Asignar facturas reales a la nota
        nota.setFacturas(reales);

        // Marcar facturas como CANCELADAS
        for (Factura f : reales) {
            f.setEstado("Cancelada");
            facturaDAO.save(f);
        }

        // Guardar nota
        notaDAO.save(nota);

        return MapearADTO.mapearNotaDeCredito(nota);
    }
}
