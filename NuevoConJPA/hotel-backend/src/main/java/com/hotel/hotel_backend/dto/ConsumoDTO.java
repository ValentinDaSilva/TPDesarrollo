package com.hotel.hotel_backend.dto;

public class ConsumoDTO {
    private String descripcion;
    private Double importe;

    public ConsumoDTO() {}

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Double getImporte() { return importe; }
    public void setImporte(Double importe) { this.importe = importe; }
}
