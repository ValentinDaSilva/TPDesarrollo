package com.hotel.hotel_backend.dto.MedioDePago;

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
