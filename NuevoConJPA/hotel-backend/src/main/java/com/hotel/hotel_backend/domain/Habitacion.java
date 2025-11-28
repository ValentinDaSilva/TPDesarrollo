package com.hotel.hotel_backend.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "habitacion")
public class Habitacion {

    @Id
    @Column(name = "numero_habitacion")
    private Integer numero;

    private String tipo;      // INDIVIDUAL, DOBLE, SUPERIOR, etc.
    private String categoria; // Estandar, Superior, Family, Suite

    @Column(name = "costo_por_noche")
    private double costoPorNoche;

    private String estado; 
    // Disponible - Reservada - Ocupada - FueraDeServicio

    public Habitacion() {}

    public Habitacion(Integer numero, String tipo, String categoria, double costoPorNoche, String estado) {
        this.numero = numero;
        this.tipo = tipo;
        this.categoria = categoria;
        this.costoPorNoche = costoPorNoche;
        this.estado = estado;
    }

    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public double getCostoPorNoche() { return costoPorNoche; }
    public void setCostoPorNoche(double costoPorNoche) { this.costoPorNoche = costoPorNoche; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
