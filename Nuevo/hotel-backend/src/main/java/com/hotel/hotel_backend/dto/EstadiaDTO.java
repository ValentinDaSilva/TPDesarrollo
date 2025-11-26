package com.hotel.hotel_backend.dto;

import java.util.List;

public class EstadiaDTO {

    private Integer id;
    private String fechaCheckIn;
    private String fechaCheckOut;
    private String estado; // EnCurso / Finalizada

    private ReservaDTO reserva;
    private HuespedDTO titular;
    private List<HuespedDTO> acompaniantes;

    private List<ConsumoDTO> consumos; // por ahora puede ir vacío

    public EstadiaDTO() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getFechaCheckIn() { return fechaCheckIn; }
    public void setFechaCheckIn(String fechaCheckIn) { this.fechaCheckIn = fechaCheckIn; }

    public String getFechaCheckOut() { return fechaCheckOut; }
    public void setFechaCheckOut(String fechaCheckOut) { this.fechaCheckOut = fechaCheckOut; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public ReservaDTO getReserva() { return reserva; }
    public void setReserva(ReservaDTO reserva) { this.reserva = reserva; }

    public HuespedDTO getTitular() { return titular; }
    public void setTitular(HuespedDTO titular) { this.titular = titular; }

    public List<HuespedDTO> getAcompaniantes() { return acompaniantes; }
    public void setAcompaniantes(List<HuespedDTO> acompaniantes) { this.acompaniantes = acompaniantes; }

    public List<ConsumoDTO> getConsumos() { return consumos; }
    public void setConsumos(List<ConsumoDTO> consumos) { this.consumos = consumos; }
}
