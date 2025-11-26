package com.hotel.hotel_backend.domain.MedioDePago;

public abstract class MedioDePago {

    private double monto;

    public MedioDePago() {}

    public MedioDePago(double monto) {
        this.monto = monto;
    }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public abstract String getTipo();
}
