package com.hotel.hotel_backend.dto.MedioDePago;

public class TarjetaDTO extends MedioDePagoDTO {

    private String tipoTarjeta;     
    private String numeroTarjeta;

    public TarjetaDTO() {}

    public TarjetaDTO(String tipoTarjeta, String numeroTarjeta, double monto) {
        super(monto);
        this.tipoTarjeta = tipoTarjeta;
        this.numeroTarjeta = numeroTarjeta;
    }

    @Override
    public String getTipo() {
        return "Tarjeta";
    }

    public String getTipoTarjeta() { return tipoTarjeta; }
    public void setTipoTarjeta(String tipoTarjeta) { this.tipoTarjeta = tipoTarjeta; }

    public String getNumeroTarjeta() { return numeroTarjeta; }
    public void setNumeroTarjeta(String numeroTarjeta) { this.numeroTarjeta = numeroTarjeta; }
}
