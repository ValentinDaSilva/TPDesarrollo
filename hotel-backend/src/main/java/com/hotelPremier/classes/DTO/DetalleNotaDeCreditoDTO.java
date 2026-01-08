package com.hotelPremier.classes.DTO;

/**
 * DTO para el detalle de una nota de crédito creada.
 */
public class DetalleNotaDeCreditoDTO {
    private Integer idNotaCredito;
    private String responsablePago;
    private Float importeNeto;
    private Float iva;
    private Float importeTotal;

    public DetalleNotaDeCreditoDTO() {}

    public DetalleNotaDeCreditoDTO(Integer idNotaCredito, String responsablePago, Float importeNeto, Float iva, Float importeTotal) {
        this.idNotaCredito = idNotaCredito;
        this.responsablePago = responsablePago;
        this.importeNeto = importeNeto;
        this.iva = iva;
        this.importeTotal = importeTotal;
    }

    public Integer getIdNotaCredito() { return idNotaCredito; }
    public void setIdNotaCredito(Integer idNotaCredito) { this.idNotaCredito = idNotaCredito; }

    public String getResponsablePago() { return responsablePago; }
    public void setResponsablePago(String responsablePago) { this.responsablePago = responsablePago; }

    public Float getImporteNeto() { return importeNeto; }
    public void setImporteNeto(Float importeNeto) { this.importeNeto = importeNeto; }

    public Float getIva() { return iva; }
    public void setIva(Float iva) { this.iva = iva; }

    public Float getImporteTotal() { return importeTotal; }
    public void setImporteTotal(Float importeTotal) { this.importeTotal = importeTotal; }
}

