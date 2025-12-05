package com.hotel.hotel_backend.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "reserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserva_id")
    private Integer id;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    // ========= TITULAR (Persona embebida) =========
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "nombre", column = @Column(name = "titular_nombre")),
            @AttributeOverride(name = "apellido", column = @Column(name = "titular_apellido")),
            @AttributeOverride(name = "telefono", column = @Column(name = "titular_telefono"))
    })
    private Persona titular;

    // ========= HABITACIONES =========
    @ManyToMany
    @JoinTable(
            name = "reserva_habitacion",
            joinColumns = @JoinColumn(name = "reserva_id"),
            inverseJoinColumns = @JoinColumn(name = "habitacion_numero")
    )
    private List<Habitacion> habitaciones;

    @Column(nullable = false)
    private String estado; // Pendiente - Confirmada - Reservada - Cancelada

@OneToOne
@JoinColumn(name = "estadia_id", nullable = true)
private Estadia estadia;

    public Reserva() {}

    public Reserva(Integer id, LocalDate fechaInicio, LocalDate fechaFin,
                   Persona titular, List<Habitacion> habitaciones, String estado) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.titular = titular;
        this.habitaciones = habitaciones;
        this.estado = estado;
    }

    // ========= GETTERS / SETTERS =========

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }

    public Persona getTitular() { return titular; }
    public void setTitular(Persona titular) { this.titular = titular; }

    public List<Habitacion> getHabitaciones() { return habitaciones; }
    public void setHabitaciones(List<Habitacion> habitaciones) { this.habitaciones = habitaciones; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
