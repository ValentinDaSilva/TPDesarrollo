package com.hotel.hotel_backend.dto;

public class HabitacionDTO {

    private int numero;
    private String tipo;
    private String categoria;
    private double costoPorNoche;
    private String estado;

    public HabitacionDTO() {}

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
