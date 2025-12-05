package com.hotel.hotel_backend.domain;

import com.hotel.hotel_backend.domain.MedioDePago.MedioDePago;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "pago")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pago_id")
    private Integer id;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private LocalTime hora;

    @Column(name = "monto_total")
    private double montoTotal;

    @ManyToOne
    @JoinColumn(name = "medio_pago_id")
    private MedioDePago medioDePago;

    @ManyToOne
    @JoinColumn(name = "factura_id")
    private Factura factura;

    @OneToMany(mappedBy = "pago", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PagoResponsable> registroResponsables;

    public Pago() {}

    public Pago(Integer id, LocalDate fecha, LocalTime hora, double montoTotal, MedioDePago medioDePago) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.montoTotal = montoTotal;
        this.medioDePago = medioDePago;
    }

    // ======== GETTERS =========

    public Integer getId() { return id; }

    public LocalDate getFecha() { return fecha; }

    public LocalTime getHora() { return hora; }

    public double getMontoTotal() { return montoTotal; }

    public MedioDePago getMedioDePago() { return medioDePago; }

    public Factura getFactura() { return factura; }

    public List<PagoResponsable> getRegistroResponsables() { return registroResponsables; }

    // ======== SETTERS =========

    public void setId(Integer id) { this.id = id; }

    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public void setHora(LocalTime hora) { this.hora = hora; }

    public void setMontoTotal(double montoTotal) { this.montoTotal = montoTotal; }

    public void setMedioDePago(MedioDePago medioDePago) { this.medioDePago = medioDePago; }

    public void setFactura(Factura factura) { this.factura = factura; }

    public void setRegistroResponsables(List<PagoResponsable> registroResponsables) {
        this.registroResponsables = registroResponsables;
    }
}
