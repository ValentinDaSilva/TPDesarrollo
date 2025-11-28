// [src/main/java/com/hotel/hotel_backend/domain/Pago.java]
package com.hotel.hotel_backend.domain;

import com.hotel.hotel_backend.domain.MedioDePago.MedioDePago;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "pago")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pago_id")
    private Integer id;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "hora", nullable = false)
    private LocalTime hora;

    @Column(name = "monto_total")
    private double montoTotal;

    // ===========================================
    //        MEDIO DE PAGO  (SIN CASCADE)
    // ===========================================
    @ManyToOne
    @JoinColumn(name = "medio_pago_id")
    private MedioDePago medioDePago;

    // ===========================================
    //                FACTURA  (SIN CASCADE)
    // ===========================================
    @ManyToOne
    @JoinColumn(name = "factura_id")
    private Factura factura;

    public Pago() {}

    public Pago(Integer id, LocalDate fecha, LocalTime hora,
                double montoTotal, MedioDePago medioDePago) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.montoTotal = montoTotal;
        this.medioDePago = medioDePago;
    }

    // ===========================
    //     GETTERS / SETTERS
    // ===========================

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }

    public double getMontoTotal() { return montoTotal; }
    public void setMontoTotal(double montoTotal) { this.montoTotal = montoTotal; }

    public MedioDePago getMedioDePago() { return medioDePago; }
    public void setMedioDePago(MedioDePago medioDePago) { this.medioDePago = medioDePago; }

    public Factura getFactura() { return factura; }
    public void setFactura(Factura factura) { this.factura = factura; }
}
