package com.hotel.hotel_backend.domain.ResponsableDePago;

import com.hotel.hotel_backend.domain.PagoResponsable;
import jakarta.persistence.*;
import java.util.List;

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

    @OneToMany(mappedBy = "responsable")
    private List<PagoResponsable> pagosRealizados;

    public ResponsableDePago() {}

    public Integer getId() { return id; }
}
