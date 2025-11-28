package com.hotel.hotel_backend.dto.MedioDePago;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "tipo"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TarjetaDTO.class, name = "tarjeta"),
        @JsonSubTypes.Type(value = ChequeDTO.class, name = "cheque"),
        @JsonSubTypes.Type(value = MonedaExtranjeraDTO.class, name = "monedaExtranjera"),
        @JsonSubTypes.Type(value = EfectivoDTO.class, name = "efectivo")
})

public abstract class MedioDePagoDTO {

    private double monto;

    public MedioDePagoDTO() {}

    public MedioDePagoDTO(double monto) {
        this.monto = monto;
    }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public abstract String getTipo();
}
