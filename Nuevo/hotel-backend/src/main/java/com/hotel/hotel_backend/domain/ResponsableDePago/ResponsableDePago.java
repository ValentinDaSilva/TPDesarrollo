package com.hotel.hotel_backend.domain.ResponsableDePago;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "tipo"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ResponsableHuesped.class, name = "huesped"),
        @JsonSubTypes.Type(value = ResponsableJuridico.class, name = "juridica")
})

public abstract class ResponsableDePago {
    private String tipo; // "huesped" o "juridica"

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}
