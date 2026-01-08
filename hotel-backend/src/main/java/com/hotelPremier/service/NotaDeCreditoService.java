package com.hotelPremier.service;

import com.hotelPremier.classes.Dominio.Factura;
import com.hotelPremier.classes.Dominio.NotaDeCredito;
import com.hotelPremier.classes.Dominio.responsablePago.PersonaFisica;
import com.hotelPremier.classes.Dominio.responsablePago.PersonaJuridica;
import com.hotelPremier.classes.DTO.DetalleNotaDeCreditoDTO;
import com.hotelPremier.classes.DTO.FacturaDTO;
import com.hotelPremier.classes.DTO.NotaDeCreditoDTO;
import com.hotelPremier.classes.Dominio.factura.observer.NotaCreditoFacturaObserver;
import com.hotelPremier.classes.exception.NegocioException;
import com.hotelPremier.classes.exception.RecursoNoEncontradoException;
import com.hotelPremier.classes.mapper.ClassMapper;
import com.hotelPremier.repository.FacturaRepository;
import com.hotelPremier.repository.NotaDeCreditoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotaDeCreditoService {

    @Autowired
    private NotaDeCreditoRepository notaRepo;

    @Autowired
    private FacturaRepository facturaRepo;

    @Autowired
    private ClassMapper mapper;

    /**
     * Busca facturas pendientes de pago por CUIT o por DNI y tipo de documento.
     * 
     * @param cuit CUIT de la persona jurídica (opcional)
     * @param dni DNI del huésped (opcional)
     * @param tipoDocumento Tipo de documento (opcional)
     * @return Lista de facturas pendientes
     * @throws NegocioException si no se proporcionan los datos necesarios o no hay facturas
     */
    public List<FacturaDTO> buscarFacturasPendientes(String cuit, String dni, String tipoDocumento) {
        if ((cuit == null || cuit.trim().isEmpty()) && 
            (dni == null || dni.trim().isEmpty() || tipoDocumento == null || tipoDocumento.trim().isEmpty())) {
            throw new NegocioException("Debe proporcionar CUIT o bien DNI y tipo de documento");
        }

        List<Factura> facturas;
        
        if (cuit != null && !cuit.trim().isEmpty()) {
            // Buscar facturas pendientes por CUIT
            facturas = facturaRepo.findAll().stream()
                    .filter(f -> f.getEstado() != null && "PENDIENTE".equals(f.getEstado()))
                    .filter(f -> f.getResponsablePago() != null && 
                                f.getResponsablePago() instanceof PersonaJuridica &&
                                cuit.equals(((PersonaJuridica) f.getResponsablePago()).getCuit()))
                    .collect(Collectors.toList());
        } else {
            // Buscar facturas pendientes por DNI y tipo de documento
            facturas = facturaRepo.findAll().stream()
                    .filter(f -> f.getEstado() != null && "PENDIENTE".equals(f.getEstado()))
                    .filter(f -> f.getResponsablePago() != null && 
                                f.getResponsablePago() instanceof PersonaFisica &&
                                dni.equals(((PersonaFisica) f.getResponsablePago()).getHuesped().getHuespedID().getDni()) &&
                                tipoDocumento.equals(((PersonaFisica) f.getResponsablePago()).getHuesped().getHuespedID().getTipoDocumento()))
                    .collect(Collectors.toList());
        }

        if (facturas.isEmpty()) {
            throw new RecursoNoEncontradoException("No hay facturas pendientes de pago a nombre de la persona ingresada");
        }

        return facturas.stream()
                .map(mapper::toDTOFactura)
                .collect(Collectors.toList());
    }

    /**
     * Registra una nota de crédito y la aplica a las facturas indicadas.
     * Retorna el detalle de la nota de crédito creada.
     */
    @Transactional
    public DetalleNotaDeCreditoDTO ingresarNotaDeCredito(NotaDeCreditoDTO dto) {

        if (dto.getImporte() == null || dto.getImporte() <= 0) {
            throw new IllegalArgumentException("El importe debe ser mayor a cero.");
        }

        if (dto.getFacturas() == null || dto.getFacturas().isEmpty()) {
            throw new IllegalArgumentException("Debe indicar al menos una factura.");
        }

        List<Integer> ids = dto.getFacturas()
                .stream()
                .map(FacturaDTO::getID)
                .toList();

        List<Factura> facturas = facturaRepo.findAllById(ids);

        if (facturas.isEmpty()) {
            throw new RecursoNoEncontradoException("No se encontraron facturas válidas con los IDs proporcionados.");
        }

        // Verificar que todas las facturas estén pendientes
        for (Factura factura : facturas) {
            if (!"PENDIENTE".equals(factura.getEstado())) {
                throw new NegocioException(
                    String.format("La factura con ID %d no está pendiente de pago", factura.getID())
                );
            }
        }

        // Calcular suma de totales de las facturas
        float sumaTotales = facturas.stream()
                .map(Factura::getTotal)
                .reduce(0f, Float::sum);

        // El importe de la nota de crédito debe ser igual a la suma de los totales
        if (Math.abs(dto.getImporte() - sumaTotales) > 0.01f) {
            throw new NegocioException(
                String.format("El importe de la nota de crédito (%.2f) debe ser igual a la suma de los totales de las facturas (%.2f)", 
                    dto.getImporte(), sumaTotales)
            );
        }

        // Calcular importe neto e IVA (asumiendo IVA del 21%)
        float importeTotal = dto.getImporte();
        float importeNeto = importeTotal / 1.21f;
        float iva = importeTotal - importeNeto;

        NotaDeCredito nota = new NotaDeCredito();
        nota.setImporte(importeTotal);

        NotaDeCredito guardada = notaRepo.save(nota);

        String responsablePago = null;
        if (!facturas.isEmpty() && facturas.get(0).getResponsablePago() != null) {
            var rp = facturas.get(0).getResponsablePago();
            if (rp instanceof PersonaJuridica pj) {
                responsablePago = pj.getRazonSocial();
            } else if (rp instanceof PersonaFisica pf) {
                responsablePago = pf.getHuesped().getNombre() + " " + pf.getHuesped().getApellido();
            }
        }

        for (Factura factura : facturas) {
            factura.setNotaDeCredito(guardada);
            prepararFacturaParaNotaCredito(factura);
            factura.aplicarNotaCredito();
            facturaRepo.save(factura);
        }

        return new DetalleNotaDeCreditoDTO(
            guardada.getId(),
            responsablePago,
            importeNeto,
            iva,
            importeTotal
        );
    }

    /**
     * Registra los observers necesarios para aplicar una nota de crédito.
     */
    private void prepararFacturaParaNotaCredito(Factura factura) {
        factura.registrarObserver(new NotaCreditoFacturaObserver());
    }
}
