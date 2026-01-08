package com.hotelPremier.classes.Dominio;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import com.hotelPremier.classes.Dominio.medioDePago.MedioDePago;

import jakarta.persistence.*;

@Entity
@Table(name = "pago")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Integer id_pago;

    @Column(name = "monto")
    private float monto;


    @Temporal(TemporalType.DATE)
    @Column(name = "fecha")
    private LocalDate fecha;

    @OneToOne
    @JoinColumn(name = "factura")
    private Factura factura;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "pago_medio",
        joinColumns = @JoinColumn(name = "id_pago"),
        inverseJoinColumns = @JoinColumn(name = "id_mediodepago")
    )
    private List<MedioDePago> listamediodepago = new ArrayList<>();

    public Pago() {
    }

    public Pago(Integer id_pago, float monto, LocalDate fecha,
                Factura factura, List<MedioDePago> listamediodepago) {
        this.id_pago = id_pago;
        this.monto = monto;
        this.fecha = fecha;
        this.factura = factura;
        this.listamediodepago = listamediodepago;
    }

    public Integer getId_pago() {
        return id_pago;
    }

    public void setId_pago(Integer id_pago) {
        this.id_pago = id_pago;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }


    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public List<MedioDePago> getListamediodepago() {
        return listamediodepago;
    }

    public void setListamediodepago(List<MedioDePago> listamediodepago) {
        this.listamediodepago = listamediodepago;
    }
}
