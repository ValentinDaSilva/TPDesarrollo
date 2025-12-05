package com.hotel.hotel_backend.dto;

import java.util.List;

public class EstadiaDTO {

    private Integer id;

    private String fechaCheckIn;
    private String horaCheckIn;

    private String fechaCheckOut;
    private String horaCheckOut;

    private String estado;

    private ReservaDTO reserva;

    private HuespedDTO titular;

    private List<HuespedDTO> acompaniantes;

    public EstadiaDTO() {
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getFechaCheckIn() { return fechaCheckIn; }
    public void setFechaCheckIn(String fechaCheckIn) { this.fechaCheckIn = fechaCheckIn; }

    public String getHoraCheckIn() { return horaCheckIn; }
    public void setHoraCheckIn(String horaCheckIn) { this.horaCheckIn = horaCheckIn; }

    public String getFechaCheckOut() { return fechaCheckOut; }
    public void setFechaCheckOut(String fechaCheckOut) { this.fechaCheckOut = fechaCheckOut; }

    public String getHoraCheckOut() { return horaCheckOut; }
    public void setHoraCheckOut(String horaCheckOut) { this.horaCheckOut = horaCheckOut; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public ReservaDTO getReserva() { return reserva; }
    public void setReserva(ReservaDTO reserva) { this.reserva = reserva; }

    public HuespedDTO getTitular() { return titular; }
    public void setTitular(HuespedDTO titular) { this.titular = titular; }

    public List<HuespedDTO> getAcompaniantes() { return acompaniantes; }
    public void setAcompaniantes(List<HuespedDTO> acompaniantes) { this.acompaniantes = acompaniantes; }
}
