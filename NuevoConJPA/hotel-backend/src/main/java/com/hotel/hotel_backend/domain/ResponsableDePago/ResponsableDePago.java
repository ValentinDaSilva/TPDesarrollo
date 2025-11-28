package com.hotel.hotel_backend.domain.ResponsableDePago;

import jakarta.persistence.*;

@Entity
@Table(name = "responsable_pago")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING)
public abstract class ResponsableDePago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "responsable_id")
    private Integer id;

    @Column(insertable = false, updatable = false)
    private String tipo;

    public ResponsableDePago() {}  // ← NECESARIO PARA JPA

    public Integer getId() { return id; }

    public String getTipo() { return tipo; }
}
