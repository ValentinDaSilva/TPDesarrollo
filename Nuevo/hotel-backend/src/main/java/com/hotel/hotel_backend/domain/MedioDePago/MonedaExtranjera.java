package com.hotel.hotel_backend.domain.MedioDePago;

public class MonedaExtranjera extends MedioDePago {

    private String tipoMoneda;
    private double montoExtranjero;
    private double cotizacion;

    public MonedaExtranjera() {}

    public MonedaExtranjera(String tipoMoneda, double montoExtranjero,
                            double cotizacion, double montoPesos) {
        super(montoPesos);
        this.tipoMoneda = tipoMoneda;
        this.montoExtranjero = montoExtranjero;
        this.cotizacion = cotizacion;
    }

    @Override
    public String getTipo() {
        return "MonedaExtranjera";
    }

    public String getTipoMoneda() { return tipoMoneda; }
    public void setTipoMoneda(String tipoMoneda) { this.tipoMoneda = tipoMoneda; }

    public double getMontoExtranjero() { return montoExtranjero; }
    public void setMontoExtranjero(double montoExtranjero) { this.montoExtranjero = montoExtranjero; }

    public double getCotizacion() { return cotizacion; }
    public void setCotizacion(double cotizacion) { this.cotizacion = cotizacion; }
}
