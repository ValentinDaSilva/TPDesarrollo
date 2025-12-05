// [src/main/java/com/hotel/hotel_backend/domain/Factura.java]
package com.hotel.hotel_backend.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.hotel.hotel_backend.domain.ResponsableDePago.ResponsableDePago;
import com.hotel.hotel_backend.domain.MedioDePago.MedioDePago;

import jakarta.persistence.*;

@Entity
@Table(name = "factura")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "factura_id")
    private Integer id;

    @Column(nullable = false)
    private LocalTime hora;

    @Column(nullable = false)
    private LocalDate fecha;

    private String tipo;
    private String estado;

    @ManyToOne
    @JoinColumn(name = "responsable_de_pago_id")
    private ResponsableDePago responsableDePago;

    @ManyToOne
    @JoinColumn(name = "estadia_id")
    private Estadia estadia;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pago> pagos;

    private double total;
    private double iva;
    private String detalle;

    @Column(name = "hora_salida")
    private LocalTime horaSalida;

    @OneToOne(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
    private NotaDeCredito notaDeCredito;
    
    public Factura() {}

    public Factura(Integer id,
                   LocalTime hora,
                   LocalDate fecha,
                   String tipo,
                   String estado,
                   ResponsableDePago responsableDePago,
                   MedioDePago medioDePago,
                   Estadia estadia,
                   List<Pago> pagos,
                   double total,
                   double iva,
                   String detalle,
                   LocalTime horaSalida) {

        this.id = id;
        this.hora = hora;
        this.fecha = fecha;
        this.tipo = tipo;
        this.estado = estado;
        this.responsableDePago = responsableDePago;
        this.estadia = estadia;
        this.pagos = pagos;
        this.total = total;
        this.iva = iva;
        this.detalle = detalle;
        this.horaSalida = horaSalida;

        if (pagos != null) {
            pagos.forEach(p -> p.setFactura(this));
        }
    }

    // ============================
    //        GETTERS / SETTERS
    // ============================

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public ResponsableDePago getResponsableDePago() { return responsableDePago; }
    public void setResponsableDePago(ResponsableDePago responsableDePago) { this.responsableDePago = responsableDePago; }


    public Estadia getEstadia() { return estadia; }
    public void setEstadia(Estadia estadia) { this.estadia = estadia; }

    public List<Pago> getPagos() { return pagos; }
    public void setPagos(List<Pago> pagos) {
        this.pagos = pagos;
        if (pagos != null) {
            pagos.forEach(p -> p.setFactura(this));
        }
    }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public double getIva() { return iva; }
    public void setIva(double iva) { this.iva = iva; }

    public String getDetalle() { return detalle; }
    public void setDetalle(String detalle) { this.detalle = detalle; }

    public LocalTime getHoraSalida() { return horaSalida; }
    public void setHoraSalida(LocalTime horaSalida) { this.horaSalida = horaSalida; }
}
