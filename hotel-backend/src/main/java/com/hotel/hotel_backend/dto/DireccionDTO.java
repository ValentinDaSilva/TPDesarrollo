package com.hotel.hotel_backend.dto;

public class DireccionDTO {
    private String calle;
    private String numero;
    private String departamento;
    private String piso;
    private String codigoPostal;
    private String localidad;
    private String provincia;
    private String pais;

    public DireccionDTO() {}

    public DireccionDTO(String calle, String numero, String departamento, String piso,
                     String codigoPostal, String localidad, String provincia, String pais) {
        this.calle = calle;
        this.numero = numero;
        this.departamento = departamento;
        this.piso = piso;
        this.codigoPostal = codigoPostal;
        this.localidad = localidad;
        this.provincia = provincia;
        this.pais = pais;
    }

    // Getters y setters
    public String getCalle() { return calle; }
    public void setCalle(String calle) { this.calle = calle; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    public String getPiso() { return piso; }
    public void setPiso(String piso) { this.piso = piso; }

    public String getCodigoPostal() { return codigoPostal; }
    public void setCodigoPostal(String codigoPostal) { this.codigoPostal = codigoPostal; }

    public String getLocalidad() { return localidad; }
    public void setLocalidad(String localidad) { this.localidad = localidad; }

    public String getProvincia() { return provincia; }
    public void setProvincia(String provincia) { this.provincia = provincia; }

    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }

    
}