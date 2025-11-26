package com.hotel.hotel_backend.dto.MedioDePago;

public class ChequeDTO extends MedioDePagoDTO {

    private String numero;
    private String fechaVencimiento;

    public ChequeDTO() {}

    public ChequeDTO(String numero, double monto, String fechaVencimiento) {
        super(monto);
        this.numero = numero;
        this.fechaVencimiento = fechaVencimiento;
    }

    @Override
    public String getTipo() {
        return "Cheque";
    }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(String fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }
}
