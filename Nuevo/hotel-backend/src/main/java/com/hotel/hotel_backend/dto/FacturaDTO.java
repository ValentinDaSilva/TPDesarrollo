package com.hotel.hotel_backend.dto;

import com.hotel.hotel_backend.dto.MedioDePago.MedioDePagoDTO;
import com.hotel.hotel_backend.dto.ResponsableDePago.ResponsableDePagoDTO;

import java.util.List;

public class FacturaDTO {

    private int id;
    private String hora;
    private String fecha;
    private String tipo;
    private String estado;

    private ResponsableDePagoDTO responsableDePago; // ← puede ser Huesped o Jurídica
    private MedioDePagoDTO medioDePago;             // ← Tarjeta/Efectivo/Cheque/Extranjera/null

    private EstadiaDTO estadia;

    private List<PagoDTO> pagos;                   // ← lista vacía al crear
    private double total;
    private double iva;
    private String detalle;
    private String horaSalida;

    public FacturaDTO(int id, String hora, String fecha, String tipo, String estado,
                      ResponsableDePagoDTO responsableDePago, MedioDePagoDTO medioDePago,
                      EstadiaDTO estadia, List<PagoDTO> pagos,
                      double total, double iva, String detalle, String horaSalida) {

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

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

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

    public String getHoraSalida() { return horaSalida; }
    public void setHoraSalida(String horaSalida) { this.horaSalida = horaSalida; }
}
