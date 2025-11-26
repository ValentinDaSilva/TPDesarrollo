package com.hotel.hotel_backend.domain;

import java.time.LocalDate;
import java.util.List;

public class Estadia {

    private int id;
    private LocalDate fechaCheckIn;
    private LocalDate fechaCheckOut;
    private String estado; // EnCurso / Finalizada

    private int idReserva; // vínculo con Reserva

    private Huesped titular;
    private List<Huesped> acompaniantes;

    public Estadia() {}

    public Estadia(
            int id,
            LocalDate fechaCheckIn,
            LocalDate fechaCheckOut,
            String estado,
            int idReserva,
            Huesped titular,
            List<Huesped> acompaniantes
    ) {
        this.id = id;
        this.fechaCheckIn = fechaCheckIn;
        this.fechaCheckOut = fechaCheckOut;
        this.estado = estado;
        this.idReserva = idReserva;
        this.titular = titular;
        this.acompaniantes = acompaniantes;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDate getFechaCheckIn() { return fechaCheckIn; }
    public void setFechaCheckIn(LocalDate fechaCheckIn) { this.fechaCheckIn = fechaCheckIn; }

    public LocalDate getFechaCheckOut() { return fechaCheckOut; }
    public void setFechaCheckOut(LocalDate fechaCheckOut) { this.fechaCheckOut = fechaCheckOut; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public int getIdReserva() { return idReserva; }
    public void setIdReserva(int idReserva) { this.idReserva = idReserva; }

    public Huesped getTitular() { return titular; }
    public void setTitular(Huesped titular) { this.titular = titular; }

    public List<Huesped> getAcompaniantes() { return acompaniantes; }
    public void setAcompaniantes(List<Huesped> acompaniantes) { this.acompaniantes = acompaniantes; }
}
