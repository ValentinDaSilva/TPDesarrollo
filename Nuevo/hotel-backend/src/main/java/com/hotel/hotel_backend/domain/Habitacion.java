package com.hotel.hotel_backend.domain;

public class Habitacion {

    private int numero;
    private String tipo;      // INDIVIDUAL, DOBLE, SUPERIOR, etc.
    private String categoria; // Estandar, Superior, Family, Suite
    private double costoPorNoche;

    private String estado; 
    // Disponible - Reservada - Ocupada - FueraDeServicio

    public Habitacion() {}

    public Habitacion(int numero, String tipo, String categoria, double costoPorNoche, String estado) {
        this.numero = numero;
        this.tipo = tipo;
        this.categoria = categoria;
        this.costoPorNoche = costoPorNoche;
        this.estado = estado;
    }

    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public double getCostoPorNoche() { return costoPorNoche; }
    public void setCostoPorNoche(double costoPorNoche) { this.costoPorNoche = costoPorNoche; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
