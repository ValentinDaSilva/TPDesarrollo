package com.hotel.hotel_backend.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "huesped")
public class Huesped {
    
    @Id
    @Column(name = "numero_documento")
    private String numeroDocumento;
    private String apellido;
    private String nombre;
    private String tipoDocumento;
    private String cuit;
    @Embedded
    private Direccion direccion;  // Composición
    private String email;
    private String ocupacion;
    private String nacionalidad;
    private String telefono;
    private LocalDate fechaNacimiento;
    public Huesped() {
    }

    public Huesped(String apellido, String nombre, String tipoDocumento, String numeroDocumento,
                   String cuit, Direccion direccion,
                   String email, String ocupacion, String nacionalidad, String telefono, LocalDate fechaNacimiento) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.cuit = cuit;
        this.direccion = direccion;
        this.email = email;
        this.ocupacion = ocupacion;
        this.nacionalidad = nacionalidad;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    

}