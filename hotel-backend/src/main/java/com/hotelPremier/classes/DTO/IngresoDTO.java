package com.hotelPremier.classes.DTO;

import java.time.LocalDate;

/**
 * DTO para representar un ingreso en el listado.
 */
public class IngresoDTO {
    private String tipoIngreso;
    private LocalDate fecha;
    private Float monto;
    private String moneda;

    public IngresoDTO() {}

    public IngresoDTO(String tipoIngreso, LocalDate fecha, Float monto, String moneda) {
        this.tipoIngreso = tipoIngreso;
        this.fecha = fecha;
        this.monto = monto;
        this.moneda = moneda;
    }

    public String getTipoIngreso() { return tipoIngreso; }
    public void setTipoIngreso(String tipoIngreso) { this.tipoIngreso = tipoIngreso; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public Float getMonto() { return monto; }
    public void setMonto(Float monto) { this.monto = monto; }

    public String getMoneda() { return moneda; }
    public void setMoneda(String moneda) { this.moneda = moneda; }
}

