package com.hotel.hotel_backend.domain.MedioDePago;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("tarjeta")
public class Tarjeta extends MedioDePago {

    private String tipoTarjeta;
    private String numeroTarjeta;

    public Tarjeta() {}

    public Tarjeta(String tipoTarjeta, String numeroTarjeta, double monto) {
        super(monto);
        this.tipoTarjeta = tipoTarjeta;
        this.numeroTarjeta = numeroTarjeta;
    }

    @Override
    public String getTipo() {
        return "tarjeta";
    }

    public String getTipoTarjeta() { return tipoTarjeta; }
    public void setTipoTarjeta(String tipoTarjeta) { this.tipoTarjeta = tipoTarjeta; }

    public String getNumeroTarjeta() { return numeroTarjeta; }
    public void setNumeroTarjeta(String numeroTarjeta) { this.numeroTarjeta = numeroTarjeta; }
}
