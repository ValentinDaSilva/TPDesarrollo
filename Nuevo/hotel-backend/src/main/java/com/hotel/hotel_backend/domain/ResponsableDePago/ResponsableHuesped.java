package com.hotel.hotel_backend.domain.ResponsableDePago;

public class ResponsableHuesped extends ResponsableDePago {

    private String apellido;
    private String nombres;
    private String documento;

    public ResponsableHuesped() {
        setTipo("huesped");
    }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }

}
