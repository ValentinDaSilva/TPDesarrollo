package com.hotel.hotel_backend.domain;

import com.hotel.hotel_backend.dto.HuespedDTO;

public class Huesped {

    private String apellido;
    private String nombre;
    private String tipoDocumento;
    private String numeroDocumento;
    private String cuit;
    private String posicionIVA;
    private Direccion direccion;  // Composición
    private String email;
    private String ocupacion;
    private String nacionalidad;
    private String direccionCompleta;
    public Huesped() {
    }

    public Huesped(String apellido, String nombre, String tipoDocumento, String numeroDocumento,
                   String cuit, String posicionIVA, Direccion direccion,
                   String email, String ocupacion, String nacionalidad) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.cuit = cuit;
        this.posicionIVA = posicionIVA;
        this.direccion = direccion;
        this.email = email;
        this.ocupacion = ocupacion;
        this.nacionalidad = nacionalidad;
        this.direccionCompleta = direccion != null
                ? direccion.getCalle() + " " + direccion.getNumero() + ", " +
                  direccion.getLocalidad() + ", " + direccion.getProvincia()
                : "";
    }

    // ======== Getters y Setters ========

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getPosicionIVA() {
        return posicionIVA;
    }

    public void setPosicionIVA(String posicionIVA) {
        this.posicionIVA = posicionIVA;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }
    public String getDireccionCompleta() {
        return direccionCompleta;
    }
    public void setDireccionCompleta(Direccion direccion) {
        this.direccionCompleta = direccion != null
                ? direccion.getCalle() + " " + direccion.getNumero() + ", " +
                  direccion.getLocalidad() + ", " + direccion.getProvincia()
                : "";
    }

    public static HuespedDTO mapearHuespedDTO(Huesped h) {
        if (h == null) return null;

        HuespedDTO x = new HuespedDTO();
        x.setNombre(h.getNombre());
        x.setApellido(h.getApellido());
        x.setTipoDocumento(h.getTipoDocumento());
        x.setNroDocumento(h.getNumeroDocumento());
        x.setOcupacion(h.getOcupacion());
        x.setNacionalidad(h.getNacionalidad());
        x.setCuit(h.getCuit());
        x.setEmail(h.getEmail());
        x.setCondicionIVA(h.getPosicionIVA());

        if (h.getDireccion() != null) {
            x.setDireccion(Direccion.mapearDireccionADTO(h.getDireccion()));
        }

        return x;
    }

    public static Huesped mapearHuespedDominio(HuespedDTO dto) {
        if (dto == null) return null;

        Direccion direccion = null;
        if (dto.getDireccion() != null) {
            direccion = Direccion.mapearDireccionDominio(dto.getDireccion());
        }

        return new Huesped(
            dto.getApellido(),
            dto.getNombre(),
            dto.getTipoDocumento(),
            dto.getNroDocumento(),
            dto.getCuit(),
            dto.getCondicionIVA(),
            direccion,
            dto.getEmail(),
            dto.getOcupacion(),
            dto.getNacionalidad()
        );
    }

}