package com.hotelPremier.classes.Dominio.responsablePago;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "responsablepago")
public abstract class ResponsablePago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_responsablepago")
    private Integer id;

    public Integer getId() {
        return id;
    }
}
