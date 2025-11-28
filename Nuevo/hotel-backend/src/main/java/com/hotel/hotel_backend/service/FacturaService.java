package com.hotel.hotel_backend.service;

import java.time.LocalDate;
import java.util.List;

import com.hotel.hotel_backend.dao.FacturaDAO;
import com.hotel.hotel_backend.dto.FacturaDTO;
import com.hotel.hotel_backend.dto.NotaDeCreditoDTO;
import com.hotel.hotel_backend.dto.PagoDTO;
import com.hotel.hotel_backend.service.Mapeo.MapearADTO;
import com.hotel.hotel_backend.service.Mapeo.MapearADominio;
import com.hotel.hotel_backend.domain.Factura;
import com.hotel.hotel_backend.domain.NotaDeCredito;

public class FacturaService {

    private final FacturaDAO facturaDAO;

    public FacturaService(FacturaDAO facturaDAO) {
        this.facturaDAO = facturaDAO;
    }

    public String generarFactura(FacturaDTO dto) {
        Factura factura = MapearADominio.mapearFactura(dto);
        facturaDAO.save(factura);
        return "Factura generada correctamente.";
    }

    public List<FacturaDTO> obtenerPendientesPorHabitacion(int numeroHabitacion) {
        List<Factura> lista = facturaDAO.findPendientesByHabitacion(numeroHabitacion);
        
        return lista.stream().map(f -> MapearADTO.mapearFactura(f)).toList(); 
    }

    public String actualizarFactura(FacturaDTO dto) {
        Factura factura = MapearADominio.mapearFactura(dto);
        facturaDAO.update(factura);
        return "Factura actualizada correctamente.";
    }

    public FacturaDTO obtenerFacturaPorId(int id) {
        Factura factura = facturaDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada con ID " + id));
        return MapearADTO.mapearFactura(factura);
    }

    public List<FacturaDTO> listarTodas() {
        List<Factura> lista = facturaDAO.findAll();
        return lista.stream().map(f-> MapearADTO.mapearFactura(f)).toList(); 
    }

    public List<PagoDTO> buscarPagoesEntreFechas(LocalDate desde, LocalDate hasta) {
        return facturaDAO.findPagosEntreFechas(desde, hasta)
                .stream()
                .map(MapearADTO::mapearPago)
                .toList();
    }

    public List<PagoDTO> buscarChequesEntreFechas(LocalDate desde, LocalDate hasta) {
        return facturaDAO.findChequesEntreFechas(desde, hasta)
                .stream()
                .map(MapearADTO::mapearPago)
                .toList();
    }

    public NotaDeCreditoDTO crearNotaDeCredito(NotaDeCreditoDTO dto) {
        NotaDeCredito nota = MapearADominio.mapearNotaDeCredito(dto);

        facturaDAO.saveNota(nota);

        return MapearADTO.mapearNotaDeCredito(nota);
    }

}
