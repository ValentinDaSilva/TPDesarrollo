package com.hotel.hotel_backend.domain.ResponsableDePago;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("juridica")
public class ResponsableJuridico extends ResponsableDePago {

    @Column(nullable = false)
    private String razonSocial;

    @Column(nullable = false)
    private String cuit;

    public ResponsableJuridico() {
        super();
    }

    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }

    public String getCuit() { return cuit; }
    public void setCuit(String cuit) { this.cuit = cuit; }
}
