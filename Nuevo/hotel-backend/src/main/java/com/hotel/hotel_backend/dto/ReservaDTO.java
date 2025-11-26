package com.hotel.hotel_backend.dto;

import java.util.List;

public class ReservaDTO {

    private Integer id;                    
    private String fechaInicio;
    private String fechaFin;
    private String estado;                 
    private TitularDTO titular;
    private List<HabitacionDTO> habitaciones;

    public ReservaDTO() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(String fechaInicio) { this.fechaInicio = fechaInicio; }

    public String getFechaFin() { return fechaFin; }
    public void setFechaFin(String fechaFin) { this.fechaFin = fechaFin; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public TitularDTO getTitular() { return titular; }
    public void setTitular(TitularDTO titular) { this.titular = titular; }

    public List<HabitacionDTO> getHabitaciones() { return habitaciones; }
    public void setHabitaciones(List<HabitacionDTO> habitaciones) { this.habitaciones = habitaciones; }

}
