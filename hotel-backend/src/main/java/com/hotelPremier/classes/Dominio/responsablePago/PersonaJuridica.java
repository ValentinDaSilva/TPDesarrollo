package com.hotelPremier.classes.Dominio.responsablePago;

import com.hotelPremier.classes.Dominio.Direccion;

import jakarta.persistence.*;

@Entity
@Table(name = "persona_juridica")
public class PersonaJuridica extends ResponsablePago {

    @Column(name = "razonsocial", nullable = false)
    private String razonSocial;

    @Column(name = "cuit", nullable = false, unique = true)
    private String cuit;

    @OneToOne(optional = false)
    @JoinColumn(name = "id_direccion_empresa", nullable = false)
    private Direccion direccionEmpresa;

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public Direccion getDireccionEmpresa() {
        return direccionEmpresa;
    }

    public void setDireccionEmpresa(Direccion direccionEmpresa) {
        this.direccionEmpresa = direccionEmpresa;
    }
}
