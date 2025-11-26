package com.hotel.hotel_backend.dto;
import com.hotel.hotel_backend.domain.MedioDePago.MedioDePago;

public class PagoDTO {

    private int id;
    private String fecha;
    private String hora;
    private double montoTotal;
    private MedioDePago medioDePago;

    public PagoDTO() {}

    public PagoDTO(int id, String fecha, String hora, double montoTotal, MedioDePago medioDePago) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.montoTotal = montoTotal;
        this.medioDePago = medioDePago;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    public double getMontoTotal() { return montoTotal; }
    public void setMontoTotal(double montoTotal) { this.montoTotal = montoTotal; }

    public MedioDePago getMedioDePago() { return medioDePago; }
    public void setMedioDePago(MedioDePago medioDePago) { this.medioDePago = medioDePago; }
}
