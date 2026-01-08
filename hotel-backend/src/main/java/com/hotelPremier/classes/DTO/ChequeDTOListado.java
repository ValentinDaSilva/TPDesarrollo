package com.hotelPremier.classes.DTO;

import java.util.Date;

/**
 * DTO para listar cheques en cartera.
 */
public class ChequeDTOListado {
    private Integer numeroCheque;
    private String banco;
    private String plaza;
    private Float monto;
    private Date fechaCobro;

    public ChequeDTOListado() {}

    public ChequeDTOListado(Integer numeroCheque, String banco, String plaza, Float monto, Date fechaCobro) {
        this.numeroCheque = numeroCheque;
        this.banco = banco;
        this.plaza = plaza;
        this.monto = monto;
        this.fechaCobro = fechaCobro;
    }

    public Integer getNumeroCheque() { return numeroCheque; }
    public void setNumeroCheque(Integer numeroCheque) { this.numeroCheque = numeroCheque; }

    public String getBanco() { return banco; }
    public void setBanco(String banco) { this.banco = banco; }

    public String getPlaza() { return plaza; }
    public void setPlaza(String plaza) { this.plaza = plaza; }

    public Float getMonto() { return monto; }
    public void setMonto(Float monto) { this.monto = monto; }

    public Date getFechaCobro() { return fechaCobro; }
    public void setFechaCobro(Date fechaCobro) { this.fechaCobro = fechaCobro; }
}

