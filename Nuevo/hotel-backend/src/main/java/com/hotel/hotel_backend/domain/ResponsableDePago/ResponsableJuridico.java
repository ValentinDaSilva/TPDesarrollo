package com.hotel.hotel_backend.domain.ResponsableDePago;

public class ResponsableJuridico extends ResponsableDePago {

    private String razonSocial;
    private String cuit;

    public ResponsableJuridico() {
        setTipo("juridica");
    }

    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }

    public String getCuit() { return cuit; }
    public void setCuit(String cuit) { this.cuit = cuit; }
}
