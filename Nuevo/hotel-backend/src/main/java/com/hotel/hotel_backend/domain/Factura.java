package com.hotel.hotel_backend.domain;

import java.util.List;
import com.hotel.hotel_backend.domain.ResponsableDePago.*;
import com.hotel.hotel_backend.domain.MedioDePago.MedioDePago;
public class Factura {

    private int id;
    private String hora;
    private String fecha;
    private String tipo;
    private String estado;

    private ResponsableDePago responsableDePago; // ← puede ser Huesped o Jurídica
    private MedioDePago medioDePago;             // ← Tarjeta/Efectivo/Cheque/Extranjera/null

    private Estadia estadia;

    private List<Pago> pagos;                   // ← lista vacía al crear
    private double total;
    private double iva;
    private String detalle;
    private String horaSalida;

    public Factura() {}

    public Factura(int id, String hora, String fecha, String tipo, String estado,
                      ResponsableDePago responsableDePago, MedioDePago medioDePago,
                      Estadia estadia, List<Pago> pagos,
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

    public ResponsableDePago getResponsableDePago() { return responsableDePago; }
    public void setResponsableDePago(ResponsableDePago responsableDePago) { this.responsableDePago = responsableDePago; }

    public MedioDePago getMedioDePago() { return medioDePago; }
    public void setMedioDePago(MedioDePago medioDePago) { this.medioDePago = medioDePago; }

    public Estadia getEstadia() { return estadia; }
    public void setEstadia(Estadia estadia) { this.estadia = estadia; }

    public List<Pago> getPagos() { return pagos; }
    public void setPagos(List<Pago> pagos) { this.pagos = pagos; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public double getIva() { return iva; }
    public void setIva(double iva) { this.iva = iva; }

    public String getDetalle() { return detalle; }
    public void setDetalle(String detalle) { this.detalle = detalle; }

    public String getHoraSalida() { return horaSalida; }
    public void setHoraSalida(String horaSalida) { this.horaSalida = horaSalida; }
}
