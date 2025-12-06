package com.hotel.hotel_backend.dto;

import com.hotel.hotel_backend.dto.MedioDePago.MedioDePagoDTO;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class PagoDTO {

    private int id;
    private LocalDate fecha;
    private LocalTime hora;
    private double montoTotal;
    private MedioDePagoDTO medioDePago;
    private FacturaDTO factura;
    private List<PagoResponsableDTO> registroResponsables;

    public PagoDTO() {}

    public PagoDTO(int id, LocalDate fecha, LocalTime hora,
                   double montoTotal, MedioDePagoDTO medioDePago) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.montoTotal = montoTotal;
        this.medioDePago = medioDePago;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }

    public double getMontoTotal() { return montoTotal; }
    public void setMontoTotal(double montoTotal) { this.montoTotal = montoTotal; }

    public MedioDePagoDTO getMedioDePago() { return medioDePago; }
    public void setMedioDePago(MedioDePagoDTO medioDePago) { this.medioDePago = medioDePago; }

    public FacturaDTO getFactura() { return factura; }
    public void setFactura(FacturaDTO factura) { this.factura = factura; }

    public List<PagoResponsableDTO> getRegistroResponsables() { return registroResponsables; }
    public void setRegistroResponsables(List<PagoResponsableDTO> registroResponsables) {
        this.registroResponsables = registroResponsables;
    }
}
