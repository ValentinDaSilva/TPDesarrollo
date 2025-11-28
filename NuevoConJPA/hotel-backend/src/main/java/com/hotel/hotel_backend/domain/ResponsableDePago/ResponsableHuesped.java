package com.hotel.hotel_backend.domain.ResponsableDePago;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("huesped")
public class ResponsableHuesped extends ResponsableDePago {

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false)
    private String nombres;

    @Column(nullable = false)
    private String documento;

    public ResponsableHuesped() {
        super();
    }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }
}
