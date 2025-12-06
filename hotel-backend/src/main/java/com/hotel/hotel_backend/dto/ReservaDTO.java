// [src/main/java/com/hotel/hotel_backend/dto/ReservaDTO.java]
package com.hotel.hotel_backend.dto;

import java.util.List;

public class ReservaDTO {

    private Integer id;
    private String fechaInicio;
    private String fechaFin;

    private PersonaDTO titular;     // ANTES: personaDTO

    private List<HabitacionDTO> habitaciones;

    private String estado;
    
    private EstadiaDTO estadia;

    public ReservaDTO() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(String fechaInicio) { this.fechaInicio = fechaInicio; }

    public String getFechaFin() { return fechaFin; }
    public void setFechaFin(String fechaFin) { this.fechaFin = fechaFin; }

    public PersonaDTO getTitular() { return titular; }
    public void setTitular(PersonaDTO titular) { this.titular = titular; }

    public List<HabitacionDTO> getHabitaciones() { return habitaciones; }
    public void setHabitaciones(List<HabitacionDTO> habitaciones) {
        this.habitaciones = habitaciones;
    }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public EstadiaDTO getEstadia() { return estadia; }
    public void setEstadia(EstadiaDTO estadia) { this.estadia = estadia; }
}
