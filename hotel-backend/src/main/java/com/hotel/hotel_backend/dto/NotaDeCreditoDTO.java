// src/main/java/com/hotel/hotel_backend/dto/NotaDeCreditoDTO.java
package com.hotel.hotel_backend.dto;

import com.hotel.hotel_backend.dto.ResponsableDePago.ResponsableDePagoDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NotaDeCreditoDTO {

    private Integer idNota;
    private LocalDate fecha;
    private ResponsableDePagoDTO responsable;
    private List<FacturaDTO> facturas;
    private String tipo;

    // ✅ Constructor vacío para JSON/Jackson
    public NotaDeCreditoDTO() {
    }

    public NotaDeCreditoDTO(Integer idNota, LocalDate fecha,
                            ResponsableDePagoDTO responsable,
                            List<FacturaDTO> facturas,
                            String tipo) {
        this.idNota = idNota;
        this.fecha = fecha != null ? fecha : LocalDate.now();
        this.responsable = responsable;
        this.facturas = facturas != null ? facturas : new ArrayList<>();
        this.tipo = tipo;
    }

    public Integer getIdNota() { return idNota; }
    public void setIdNota(Integer idNota) { this.idNota = idNota; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public ResponsableDePagoDTO getResponsable() { return responsable; }
    public void setResponsable(ResponsableDePagoDTO responsable) { this.responsable = responsable; }

    public List<FacturaDTO> getFacturas() { return facturas; }
    public void setFacturas(List<FacturaDTO> facturas) { this.facturas = facturas; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}
