package com.hotel.hotel_backend.service;

import com.hotel.hotel_backend.dao.FacturaDAO;
import com.hotel.hotel_backend.dto.FacturaDTO;
import com.hotel.hotel_backend.domain.Factura;

public class FacturaService {

    private final FacturaDAO facturaDAO;

    public FacturaService(FacturaDAO facturaDAO) {
        this.facturaDAO = facturaDAO;
    }

    // Obtener facturas pendientes por habitación
    public List<FacturaDTO> obtenerPendientesPorHabitacion(int numeroHabitacion) {

        return facturaDAO.findPendientesByHabitacion(numeroHabitacion)
                .stream()
                .map(this::mapearFacturaADTO)
                .toList();
    }

    // Actualizar factura completa
    public FacturaDTO actualizarFactura(FacturaDTO dto) {


        facturaDAO.update(dto);

        return dto;
    }
}
