// [src/main/java/com/hotel/hotel_backend/domain/MedioDePago/MedioDePago.java]
package com.hotel.hotel_backend.domain.MedioDePago;

import jakarta.persistence.*;

@Entity
@Table(name = "medio_de_pago")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING)
public abstract class MedioDePago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medio_pago_id")
    private Integer id;

    private double monto;

    public MedioDePago() {}

    public MedioDePago(double monto) {
        this.monto = monto;
    }

    public Integer getId() { return id; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public abstract String getTipo();
}
