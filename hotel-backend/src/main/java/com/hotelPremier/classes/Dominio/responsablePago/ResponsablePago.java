package com.hotelPremier.classes.Dominio.responsablePago;

import java.util.List;

import com.hotelPremier.classes.Dominio.Direccion;
import com.hotelPremier.classes.Dominio.Factura;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "responsablepago")
public abstract class ResponsablePago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_responsablepago")
    private Integer id;

    @OneToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_direccion", nullable = true)
    private Direccion direccion;

    @OneToMany(mappedBy = "responsablepago")
    private List<Factura> facturas;

    public Integer getId() { return id; }

    public Direccion getDireccion() { return direccion; }
    public void setDireccion(Direccion direccion) { this.direccion = direccion; }
}
