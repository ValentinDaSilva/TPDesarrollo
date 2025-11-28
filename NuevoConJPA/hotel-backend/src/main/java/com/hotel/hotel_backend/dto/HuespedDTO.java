// [src/main/java/com/hotel/hotel_backend/dto/HuespedDTO.java]
package com.hotel.hotel_backend.dto;

public class HuespedDTO {
    private String nombre;
    private String apellido;
    private String tipoDocumento;
    private String numeroDocumento;
    private String ocupacion;
    private String nacionalidad;
    private String cuit;
    private String email;

    private DireccionDTO direccion; // puede ser null
    private String condicionIVA;    // puede ser null

    public HuespedDTO() {}

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getTipoDocumento() { return tipoDocumento; }
    public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }

    public String getNumeroDocumento() { return numeroDocumento; }
    public void setNumeroDocumento(String numeroDocumento) { this.numeroDocumento = numeroDocumento; }

    public String getOcupacion() { return ocupacion; }
    public void setOcupacion(String ocupacion) { this.ocupacion = ocupacion; }

    public String getNacionalidad() { return nacionalidad; }
    public void setNacionalidad(String nacionalidad) { this.nacionalidad = nacionalidad; }

    public String getCuit() { return cuit; }
    public void setCuit(String cuit) { this.cuit = cuit; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public DireccionDTO getDireccion() { return direccion; }
    public void setDireccion(DireccionDTO direccion) { this.direccion = direccion; }

    public String getCondicionIVA() { return condicionIVA; }
    public void setCondicionIVA(String condicionIVA) { this.condicionIVA = condicionIVA; }
}
