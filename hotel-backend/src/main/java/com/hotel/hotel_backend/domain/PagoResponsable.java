package com.hotel.hotel_backend.domain;

import com.hotel.hotel_backend.domain.ResponsableDePago.ResponsableDePago;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "pago_responsable")
public class PagoResponsable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pago_responsable_id")
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "pago_id")
    private Pago pago;

    @ManyToOne(optional = false)
    @JoinColumn(name = "factura_id")
    private Factura factura;

    @ManyToOne(optional = false)
    @JoinColumn(name = "responsable_id")
    private ResponsableDePago responsable;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private LocalTime hora;

    public PagoResponsable() {}

    public PagoResponsable(Pago pago, Factura factura, ResponsableDePago responsable) {
        this.pago = pago;
        this.factura = factura;
        this.responsable = responsable;
        this.fecha = LocalDate.now();
        this.hora = LocalTime.now();
    }

    public Integer getId() { return id; }
    public Pago getPago() { return pago; }
    public void setPago(Pago pago) { this.pago = pago; }
    public Factura getFactura() { return factura; }
    public void setFactura(Factura factura) { this.factura = factura; }
    public ResponsableDePago getResponsable() { return responsable; }
    public void setResponsable(ResponsableDePago responsable) { this.responsable = responsable; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }
}
