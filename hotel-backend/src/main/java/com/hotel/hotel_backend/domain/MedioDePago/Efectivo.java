package com.hotel.hotel_backend.domain.MedioDePago;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("efectivo")
public class Efectivo extends MedioDePago {

    public Efectivo() {}

    public Efectivo(double monto) {
        super(monto);
    }

    @Override
    public String getTipo() {
        return "efectivo";
    }
}
