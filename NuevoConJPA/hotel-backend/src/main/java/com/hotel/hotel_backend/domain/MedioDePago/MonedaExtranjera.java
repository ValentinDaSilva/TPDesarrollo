package com.hotel.hotel_backend.domain.MedioDePago;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("monedaExtranjera")
public class MonedaExtranjera extends MedioDePago {

    private String tipoMoneda;
    private double montoExtranjero;
    private double cotizacion;

    public MonedaExtranjera() {}

    public MonedaExtranjera(String tipoMoneda, double montoExtranjero,
                            double cotizacion, double monto) {
        super(monto);
        this.tipoMoneda = tipoMoneda;
        this.montoExtranjero = montoExtranjero;
        this.cotizacion = cotizacion;
    }

    @Override
    public String getTipo() {
        return "monedaExtranjera";
    }

    public String getTipoMoneda() { return tipoMoneda; }
    public void setTipoMoneda(String tipoMoneda) { this.tipoMoneda = tipoMoneda; }

    public double getMontoExtranjero() { return montoExtranjero; }
    public void setMontoExtranjero(double montoExtranjero) { this.montoExtranjero = montoExtranjero; }

    public double getCotizacion() { return cotizacion; }
    public void setCotizacion(double cotizacion) { this.cotizacion = cotizacion; }
}
