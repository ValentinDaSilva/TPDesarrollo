package com.hotelPremier.classes.DTO;

import java.util.List;
import java.util.Map;

/**
 * DTO para el listado de ingresos con totalización.
 */
public class ListadoIngresosDTO {
    private List<IngresoDTO> ingresos;
    private Map<String, Map<String, Float>> totales; // tipoIngreso -> moneda -> total

    public ListadoIngresosDTO() {}

    public ListadoIngresosDTO(List<IngresoDTO> ingresos, Map<String, Map<String, Float>> totales) {
        this.ingresos = ingresos;
        this.totales = totales;
    }

    public List<IngresoDTO> getIngresos() { return ingresos; }
    public void setIngresos(List<IngresoDTO> ingresos) { this.ingresos = ingresos; }

    public Map<String, Map<String, Float>> getTotales() { return totales; }
    public void setTotales(Map<String, Map<String, Float>> totales) { this.totales = totales; }
}

