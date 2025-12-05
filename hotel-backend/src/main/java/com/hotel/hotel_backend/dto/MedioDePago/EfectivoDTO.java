package com.hotel.hotel_backend.dto.MedioDePago;

public class EfectivoDTO extends MedioDePagoDTO {

    public EfectivoDTO() {}

    public EfectivoDTO(double monto) {
        super(monto);
    }

    @Override
    public String getTipo() {
        return "Efectivo";
    }
}
