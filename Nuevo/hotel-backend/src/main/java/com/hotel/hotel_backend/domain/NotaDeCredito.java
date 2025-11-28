package com.hotel.hotel_backend.domain;

import com.hotel.hotel_backend.domain.ResponsableDePago.ResponsableDePago;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NotaDeCredito {

    private Integer idNota;
    private LocalDate fecha;
    private ResponsableDePago responsable;
    private List<Factura> facturas;
    private String tipo;



    public NotaDeCredito(Integer idNota, LocalDate fecha, 
                         ResponsableDePago responsable, 
                         List<Factura> facturas,
                         String tipo) {
            this.idNota = idNota;
            this.fecha = fecha != null ? fecha : LocalDate.now();
            this.responsable = responsable;
            this.facturas = facturas != null ? facturas : new ArrayList<>();
            this.tipo = tipo;
    }

    public Integer getIdNota() { return idNota; }
    public void setIdNota(Integer idNota) { this.idNota = idNota; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public ResponsableDePago getResponsable() { return responsable; }
    public void setResponsable(ResponsableDePago responsable) { this.responsable = responsable; }

    public List<Factura> getFacturas() { return facturas; }
    public void setFacturas(List<Factura> facturas) { this.facturas = facturas; }


    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

}
