package com.hotelPremier.classes.DTO;

import java.util.List;

/**
 * DTO para el listado de cheques con total.
 */
public class ListadoChequesDTO {
    private List<ChequeDTOListado> cheques;
    private Float totalMonto;

    public ListadoChequesDTO() {}

    public ListadoChequesDTO(List<ChequeDTOListado> cheques, Float totalMonto) {
        this.cheques = cheques;
        this.totalMonto = totalMonto;
    }

    public List<ChequeDTOListado> getCheques() { return cheques; }
    public void setCheques(List<ChequeDTOListado> cheques) { this.cheques = cheques; }

    public Float getTotalMonto() { return totalMonto; }
    public void setTotalMonto(Float totalMonto) { this.totalMonto = totalMonto; }
}

