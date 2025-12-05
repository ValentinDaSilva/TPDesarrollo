package com.hotel.hotel_backend.dto.ResponsableDePago;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "tipo"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ResponsableHuespedDTO.class, name = "huesped"),
        @JsonSubTypes.Type(value = ResponsableJuridicoDTO.class, name = "juridica")
})
public abstract class ResponsableDePagoDTO {
    private String tipo; // "huesped" o "juridica"

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}
