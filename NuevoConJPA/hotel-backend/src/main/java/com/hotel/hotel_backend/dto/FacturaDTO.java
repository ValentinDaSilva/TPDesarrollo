// [src/main/java/com/hotel/hotel_backend/dto/FacturaDTO.java]
package com.hotel.hotel_backend.dto;

import com.hotel.hotel_backend.dto.MedioDePago.MedioDePagoDTO;
import com.hotel.hotel_backend.dto.ResponsableDePago.ResponsableDePagoDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class FacturaDTO {

    private int id;
    private LocalTime hora;
    private LocalDate fecha;
    private String tipo;
    private String estado;

    private ResponsableDePagoDTO responsableDePago; 
    private MedioDePagoDTO medioDePago;

    private EstadiaDTO estadia;

    private List<PagoDTO> pagos;
    private double total;
    private double iva;
    private String detalle;
    private LocalTime horaSalida;

    public FacturaDTO() {}

    public FacturaDTO(int id, LocalTime hora, LocalDate fecha, String tipo, String estado,
                      ResponsableDePagoDTO responsableDePago, MedioDePagoDTO medioDePago,
                      EstadiaDTO estadia, List<PagoDTO> pagos,
                      double total, double iva, String detalle, LocalTime horaSalida) {

        this.id = id;
        this.hora = hora;
        this.fecha = fecha;
        this.tipo = tipo;
        this.estado = estado;
        this.responsableDePago = responsableDePago;
        this.medioDePago = medioDePago;
        this.estadia = estadia;
        this.pagos = pagos;
        this.total = total;
        this.iva = iva;
        this.detalle = detalle;
        this.horaSalida = horaSalida;
    }

    // ---------------- Getters & Setters ------------------

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public ResponsableDePagoDTO getResponsableDePago() { return responsableDePago; }
    public void setResponsableDePago(ResponsableDePagoDTO responsableDePago) { this.responsableDePago = responsableDePago; }

    public MedioDePagoDTO getMedioDePago() { return medioDePago; }
    public void setMedioDePago(MedioDePagoDTO medioDePago) { this.medioDePago = medioDePago; }

    public EstadiaDTO getEstadia() { return estadia; }
    public void setEstadia(EstadiaDTO estadia) { this.estadia = estadia; }

    public List<PagoDTO> getPagos() { return pagos; }
    public void setPagos(List<PagoDTO> pagos) { this.pagos = pagos; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public double getIva() { return iva; }
    public void setIva(double iva) { this.iva = iva; }

    public String getDetalle() { return detalle; }
    public void setDetalle(String detalle) { this.detalle = detalle; }

    public LocalTime getHoraSalida() { return horaSalida; }
    public void setHoraSalida(LocalTime horaSalida) { this.horaSalida = horaSalida; }
}
