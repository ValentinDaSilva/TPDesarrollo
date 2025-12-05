package com.hotel.hotel_backend.domain.MedioDePago;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("cheque")
public class Cheque extends MedioDePago {

    private String numero;
    private String fechaVencimiento;

    public Cheque() {}

    public Cheque(String numero, double monto, String fechaVencimiento) {
        super(monto);
        this.numero = numero;
        this.fechaVencimiento = fechaVencimiento;
    }

    @Override
    public String getTipo() {
        return "cheque";
    }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(String fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }
}
