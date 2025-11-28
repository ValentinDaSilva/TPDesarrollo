package com.hotel.hotel_backend.domain.MedioDePago;

public class Efectivo extends MedioDePago {

    public Efectivo() {}

    public Efectivo(double monto) {
        super(monto);
    }

    @Override
    public String getTipo() {
        return "efectivo";
    }
}
