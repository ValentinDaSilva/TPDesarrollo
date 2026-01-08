package com.hotelPremier.service;

import com.hotelPremier.classes.DTO.IngresoDTO;
import com.hotelPremier.classes.DTO.ListadoIngresosDTO;
import com.hotelPremier.classes.Dominio.Pago;
import com.hotelPremier.classes.Dominio.medioDePago.MedioDePago;
import com.hotelPremier.classes.Dominio.medioDePago.MonedaExtranjera;
import com.hotelPremier.classes.Dominio.medioDePago.MonedaLocal;
import com.hotelPremier.classes.exception.NegocioException;
import com.hotelPremier.repository.PagoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class IngresoService {

    @Autowired
    private PagoRepository pagoRepository;

    /**
     * Lista los ingresos de un período ordenados por fecha.
     * Totaliza por tipo de ingreso y moneda.
     * 
     * @param desdeFecha Fecha inicial del período
     * @param hastaFecha Fecha final del período
     * @return ListadoIngresosDTO con ingresos y totales
     * @throws NegocioException si las fechas son inválidas
     */
    public ListadoIngresosDTO listarIngresos(LocalDate desdeFecha, LocalDate hastaFecha) {
        // Validar fechas
        validarFechas(desdeFecha, hastaFecha);

        // Buscar todos los pagos en el rango de fechas
        List<Pago> pagos = pagoRepository.findAll().stream()
                .filter(p -> p.getFecha() != null &&
                            !p.getFecha().isBefore(desdeFecha) &&
                            !p.getFecha().isAfter(hastaFecha))
                .sorted((p1, p2) -> p1.getFecha().compareTo(p2.getFecha()))
                .collect(Collectors.toList());

        List<IngresoDTO> ingresos = new ArrayList<>();
        Map<String, Map<String, Float>> totales = new HashMap<>();

        // Procesar cada pago y sus medios de pago
        for (Pago pago : pagos) {
            if (pago.getListamediodepago() != null) {
                for (MedioDePago medio : pago.getListamediodepago()) {
                    String tipoIngreso = obtenerTipoIngreso(medio);
                    String moneda = obtenerMoneda(medio);
                    
                    IngresoDTO ingreso = new IngresoDTO(
                        tipoIngreso,
                        pago.getFecha(),
                        medio.getMonto(),
                        moneda
                    );
                    ingresos.add(ingreso);

                    // Acumular totales
                    totales.putIfAbsent(tipoIngreso, new HashMap<>());
                    totales.get(tipoIngreso).putIfAbsent(moneda, 0f);
                    totales.get(tipoIngreso).put(
                        moneda,
                        totales.get(tipoIngreso).get(moneda) + medio.getMonto()
                    );
                }
            }
        }

        return new ListadoIngresosDTO(ingresos, totales);
    }

    /**
     * Obtiene el tipo de ingreso a partir del medio de pago.
     */
    private String obtenerTipoIngreso(MedioDePago medio) {
        String className = medio.getClass().getSimpleName();
        // Convertir nombres de clase a formato estándar
        if (className.equals("Cheque")) {
            return "CHEQUE";
        } else if (className.equals("MonedaLocal")) {
            return "MONEDA_LOCAL";
        } else if (className.equals("MonedaExtranjera")) {
            return "MONEDA_EXTRANJERA";
        } else if (className.equals("TarjetaCredito")) {
            return "TARJETA_CREDITO";
        } else if (className.equals("TarjetaDebito")) {
            return "TARJETA_DEBITO";
        } else {
            return className.toUpperCase();
        }
    }

    /**
     * Obtiene la moneda a partir del medio de pago.
     */
    private String obtenerMoneda(MedioDePago medio) {
        if (medio instanceof MonedaLocal ml) {
            return ml.getTipoMoneda() != null ? ml.getTipoMoneda() : "ARS";
        } else if (medio instanceof MonedaExtranjera me) {
            return me.getTipoMoneda() != null ? me.getTipoMoneda() : "USD";
        } else {
            // Para cheques y tarjetas, se asume moneda local
            return "ARS";
        }
    }

    /**
     * Valida las fechas del período.
     */
    private void validarFechas(LocalDate desdeFecha, LocalDate hastaFecha) {
        if (desdeFecha == null) {
            throw new NegocioException("La fecha inicial no puede ser nula");
        }
        if (hastaFecha == null) {
            throw new NegocioException("La fecha final no puede ser nula");
        }
        if (hastaFecha.isBefore(desdeFecha)) {
            throw new NegocioException("La fecha final no puede ser anterior a la fecha inicial");
        }
    }
}

