package com.hotelPremier.service;

import com.hotelPremier.classes.DTO.ChequeDTOListado;
import com.hotelPremier.classes.DTO.ListadoChequesDTO;
import com.hotelPremier.classes.Dominio.medioDePago.Cheque;
import com.hotelPremier.classes.exception.NegocioException;
import com.hotelPremier.repository.MedioDePagoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChequeService {

    @Autowired
    private MedioDePagoRepository medioDePagoRepository;

    /**
     * Lista los cheques en cartera ordenados por fecha de cobro.
     * 
     * @param desdeFecha Fecha inicial del período
     * @param hastaFecha Fecha final del período
     * @return ListadoChequesDTO con cheques y total de montos
     * @throws NegocioException si las fechas son inválidas
     */
    public ListadoChequesDTO listarCheques(Date desdeFecha, Date hastaFecha) {
        // Validar fechas
        validarFechas(desdeFecha, hastaFecha);

        // Buscar cheques en el rango de fechas (ordenados por fecha de cobro/plazo)
        List<Cheque> cheques = medioDePagoRepository.findChequesPorRangoFechas(desdeFecha, hastaFecha);

        // Convertir a DTOs
        List<ChequeDTOListado> chequesDTO = cheques.stream()
                .map(cheque -> new ChequeDTOListado(
                    cheque.getNumeroCheque(),
                    cheque.getBanco(),
                    null, // Plaza no existe en la entidad actual
                    cheque.getMonto(),
                    cheque.getPlazo()
                ))
                .collect(Collectors.toList());

        // Calcular total
        Float totalMonto = cheques.stream()
                .map(Cheque::getMonto)
                .reduce(0f, Float::sum);

        return new ListadoChequesDTO(chequesDTO, totalMonto);
    }

    /**
     * Valida las fechas del período.
     */
    private void validarFechas(Date desdeFecha, Date hastaFecha) {
        if (desdeFecha == null) {
            throw new NegocioException("La fecha inicial no puede ser nula");
        }
        if (hastaFecha == null) {
            throw new NegocioException("La fecha final no puede ser nula");
        }
        if (hastaFecha.before(desdeFecha)) {
            throw new NegocioException("La fecha final no puede ser anterior a la fecha inicial");
        }
    }
}

