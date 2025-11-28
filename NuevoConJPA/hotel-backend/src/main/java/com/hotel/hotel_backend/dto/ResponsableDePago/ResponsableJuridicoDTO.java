package com.hotel.hotel_backend.dto.ResponsableDePago;

public class ResponsableJuridicoDTO extends ResponsableDePagoDTO {

    private String razonSocial;
    private String cuit;

    public ResponsableJuridicoDTO() {
        setTipo("juridica");
    }

    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }

    public String getCuit() { return cuit; }
    public void setCuit(String cuit) { this.cuit = cuit; }
}
