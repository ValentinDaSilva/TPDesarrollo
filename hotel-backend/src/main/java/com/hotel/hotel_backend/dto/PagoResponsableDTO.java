package com.hotel.hotel_backend.dto;

import com.hotel.hotel_backend.dto.ResponsableDePago.ResponsableDePagoDTO;
import java.time.LocalDate;
import java.time.LocalTime;

public class PagoResponsableDTO {

    private Integer id;

    private PagoDTO pago;

    private FacturaDTO factura;

    private ResponsableDePagoDTO responsable;

    private LocalDate fecha;

    private LocalTime hora;

    public PagoResponsableDTO() {}

    public PagoResponsableDTO(PagoDTO pago, FacturaDTO factura, ResponsableDePagoDTO responsable) {
        this.pago = pago;
        this.factura = factura;
        this.responsable = responsable;
        this.fecha = LocalDate.now();
        this.hora = LocalTime.now();
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public PagoDTO getPago() { return pago; }
    public void setPago(PagoDTO pago) { this.pago = pago; }

    public FacturaDTO getFactura() { return factura; }
    public void setFactura(FacturaDTO factura) { this.factura = factura; }

    public ResponsableDePagoDTO getResponsable() { return responsable; }
    public void setResponsable(ResponsableDePagoDTO responsable) { this.responsable = responsable; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }
}
