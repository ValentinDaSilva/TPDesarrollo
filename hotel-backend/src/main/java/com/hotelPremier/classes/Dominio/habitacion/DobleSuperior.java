package com.hotelPremier.classes.Dominio.habitacion;

import com.hotelPremier.classes.Dominio.Habitacion;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("DOBLESUPERIOR")
public class DobleSuperior  extends Habitacion{
    @Column(name = "camasindividuales")
    private Integer camasIndividuales;
    @Column(name = "camadoble")
    private Integer camaDoble;
}
