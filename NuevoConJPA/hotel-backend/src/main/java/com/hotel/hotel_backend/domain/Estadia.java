// src/main/java/com/hotel/hotel_backend/domain/Estadia.java
package com.hotel.hotel_backend.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "estadia")
public class Estadia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "estadia_id")
    private Integer id;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaCheckIn;

    @Column(name = "hora_inicio")
    private LocalTime horaCheckIn;

    // ✅ Ahora permiten NULL hasta el check-out
    @Column(name = "fecha_fin")
    private LocalDate fechaCheckOut;

    @Column(name = "hora_fin")
    private LocalTime horaCheckOut;

    @Column(nullable = false)
    private String estado;

    @ManyToOne
    @JoinColumn(name = "reserva_id", nullable = false)
    private Reserva reserva;

    @ManyToOne
    @JoinColumn(name = "titular_documento")
    private Huesped titular;

    @ManyToMany
    @JoinTable(
            name = "estadia_acompaniantes",
            joinColumns = @JoinColumn(name = "estadia_id"),
            inverseJoinColumns = @JoinColumn(name = "acompaniante_documento")
    )
    private List<Huesped> acompaniantes;

    public Estadia() {}

    public Estadia(Integer id,
                   LocalDate fechaCheckIn,
                   LocalTime horaCheckIn,
                   LocalDate fechaCheckOut,
                   LocalTime horaCheckOut,
                   String estado,
                   Reserva reserva,
                   Huesped titular,
                   List<Huesped> acompaniantes
    ) {
        this.id = id;
        this.fechaCheckIn = fechaCheckIn;
        this.horaCheckIn = horaCheckIn;
        this.fechaCheckOut = fechaCheckOut;
        this.horaCheckOut = horaCheckOut;
        this.estado = estado;
        this.reserva = reserva;
        this.titular = titular;
        this.acompaniantes = acompaniantes;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public LocalDate getFechaCheckIn() { return fechaCheckIn; }
    public void setFechaCheckIn(LocalDate fechaCheckIn) { this.fechaCheckIn = fechaCheckIn; }

    public LocalTime getHoraCheckIn() { return horaCheckIn; }
    public void setHoraCheckIn(LocalTime horaCheckIn) { this.horaCheckIn = horaCheckIn; }

    public LocalDate getFechaCheckOut() { return fechaCheckOut; }
    public void setFechaCheckOut(LocalDate fechaCheckOut) { this.fechaCheckOut = fechaCheckOut; }

    public LocalTime getHoraCheckOut() { return horaCheckOut; }
    public void setHoraCheckOut(LocalTime horaCheckOut) { this.horaCheckOut = horaCheckOut; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Reserva getReserva() { return reserva; }
    public void setReserva(Reserva reserva) { this.reserva = reserva; }

    public Huesped getTitular() { return titular; }
    public void setTitular(Huesped titular) { this.titular = titular; }

    public List<Huesped> getAcompaniantes() { return acompaniantes; }
    public void setAcompaniantes(List<Huesped> acompaniantes) { this.acompaniantes = acompaniantes; }
}
