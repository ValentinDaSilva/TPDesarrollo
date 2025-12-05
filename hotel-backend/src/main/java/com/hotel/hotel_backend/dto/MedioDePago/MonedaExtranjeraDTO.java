package com.hotel.hotel_backend.dto.MedioDePago;

public class MonedaExtranjeraDTO extends MedioDePagoDTO {

    private String tipoMoneda;
    private double montoExtranjero;
    private double cotizacion;

    public MonedaExtranjeraDTO() {}

    public MonedaExtranjeraDTO(String tipoMoneda, double montoExtranjero,
                            double cotizacion, double monto) {
        super(monto);
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
