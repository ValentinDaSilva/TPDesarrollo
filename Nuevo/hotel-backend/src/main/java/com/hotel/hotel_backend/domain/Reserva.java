package com.hotel.hotel_backend.domain;

import java.time.LocalDate;
import java.util.List;

public class Reserva {

    private int id;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    private Titular titular;  // Titular genérico (NO debe ser huésped obligatorio)
    private List<Integer> habitaciones; // números de habitación

    private String estado; // Pendiente - Confirmada - Cancelada

    public Reserva() {}

    public Reserva(int id, LocalDate fechaInicio, LocalDate fechaFin,
                   Titular titular, List<Integer> habitaciones, String estado) {

        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.titular = titular;
        this.habitaciones = habitaciones;
        this.estado = estado;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }

    public Titular getTitular() { return titular; }
    public void setTitular(Titular titular) { this.titular = titular; }

    public List<Integer> getHabitaciones() { return habitaciones; }
    public void setHabitaciones(List<Integer> habitaciones) { this.habitaciones = habitaciones; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
