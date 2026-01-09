package com.hotelPremier.service;

import com.hotelPremier.classes.DTO.DireccionDTO;
import com.hotelPremier.classes.DTO.ResponsablePagoDTO;
import com.hotelPremier.classes.exception.NegocioException;
import com.hotelPremier.classes.exception.RecursoNoEncontradoException;
import com.hotelPremier.classes.mapper.ClassMapper;
import com.hotelPremier.repository.DireccionRepository;
import com.hotelPremier.repository.FacturaRepository;
import com.hotelPremier.repository.HuespedRepository;
import com.hotelPremier.repository.ResponsablePagoRepository;
import com.hotelPremier.classes.Dominio.Direccion;
import com.hotelPremier.classes.Dominio.Factura;
import com.hotelPremier.classes.Dominio.Huesped;
import com.hotelPremier.classes.Dominio.HuespedID;
import com.hotelPremier.classes.Dominio.responsablePago.PersonaFisica;
import com.hotelPremier.classes.Dominio.responsablePago.PersonaJuridica;
import com.hotelPremier.classes.Dominio.responsablePago.ResponsablePago;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ResponsablePagoService {

    @Autowired
    private ResponsablePagoRepository responsablePagoRepository;

    @Autowired
    private HuespedRepository huespedRepository;

    @Autowired
    private DireccionRepository direccionRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private ClassMapper classMapper;

    /**
     * Resultado estándar para controller
     */
    public record ResponsablePagoResult(Integer id, String razonSocial) {}

    // ==========================================================
    // BUSCAR / CREAR RESPONSABLE DE PAGO
    // ==========================================================

    @Transactional
    public ResponsablePagoDTO buscarResponsablePago(
            String dni,
            String tipoDocumento,
            String cuit
    ) {

        // ===============================
        // PERSONA JURIDICA
        // ===============================
        if (cuit != null && !cuit.trim().isEmpty()) {

            PersonaJuridica pj = responsablePagoRepository
                    .findPersonaJuridicaByCuit(cuit)
                    .orElseThrow(() -> new RecursoNoEncontradoException(
                            "No se encontró responsable de pago con CUIT: " + cuit
                    ));

            return classMapper.toDTO(pj);
        }

        // ===============================
        // PERSONA FISICA
        // ===============================
        if (dni == null || dni.trim().isEmpty()
                || tipoDocumento == null || tipoDocumento.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Debe proporcionar CUIT o bien DNI y tipoDocumento"
            );
        }

        // 1️⃣ Buscar PersonaFisica existente
        var personaFisicaOpt =
                responsablePagoRepository.findPersonaFisicaByDniAndTipoDocumento(dni, tipoDocumento);

        if (personaFisicaOpt.isPresent()) {
            return classMapper.toDTO(personaFisicaOpt.get());
        }

        // 2️⃣ Buscar Huesped
        HuespedID huespedID = new HuespedID();
        huespedID.setDni(dni);
        huespedID.setTipoDocumento(tipoDocumento);

        Huesped huesped = huespedRepository.findById(huespedID)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        String.format(
                                "No se encontró huésped con DNI: %s y tipoDocumento: %s",
                                dni, tipoDocumento
                        )
                ));

        // 3️⃣ Si el huésped ya tiene responsablePago, devolverlo
        if (huesped.getResponsablePago() != null
                && huesped.getResponsablePago().getId() != null) {

            return classMapper.toDTO(huesped.getResponsablePago());
        }

        // 4️⃣ Crear PersonaFisica
        PersonaFisica nuevaPersonaFisica = new PersonaFisica();
        nuevaPersonaFisica.setHuesped(huesped);

        PersonaFisica personaFisicaGuardada =
                responsablePagoRepository.save(nuevaPersonaFisica);

        // 5️⃣ Relación bidireccional
        huesped.setResponsablePago(personaFisicaGuardada);
        huespedRepository.save(huesped);

        return classMapper.toDTO(personaFisicaGuardada);
    }

    // ==========================================================
    // ALTA EXPLÍCITA
    // ==========================================================

    @Transactional
    public ResponsablePagoDTO altaResponsablePago(ResponsablePagoDTO dto) {

        if (dto == null) {
            throw new NegocioException("El DTO no puede ser nulo");
        }

        if (dto.getTipo() == null || dto.getTipo().trim().isEmpty()) {
            throw new NegocioException("Debe indicar tipo FISICA o JURIDICA");
        }

        String tipo = dto.getTipo().toUpperCase().trim();

        return switch (tipo) {
            case "FISICA" -> altaPersonaFisica(dto);
            case "JURIDICA" -> altaPersonaJuridica(dto);
            default -> throw new NegocioException("Tipo de responsable de pago inválido");
        };
    }

    // ==========================================================
    // ALTA PERSONA FISICA
    // ==========================================================

    private ResponsablePagoDTO altaPersonaFisica(ResponsablePagoDTO dto) {

        String dni = dto.getDni();
        String tipoDocumento = dto.getTipoDocumento();

        if (dni == null || dni.trim().isEmpty()) {
            throw new NegocioException("Debe especificar DNI");
        }

        if (tipoDocumento == null || tipoDocumento.trim().isEmpty()) {
            throw new NegocioException("Debe especificar tipo de documento");
        }

        var existente =
                responsablePagoRepository.findPersonaFisicaByDniAndTipoDocumento(dni, tipoDocumento);

        if (existente.isPresent()) {
            throw new NegocioException(
                    "Ya existe un responsable de pago PersonaFisica con ese DNI"
            );
        }

        HuespedID huespedID = new HuespedID();
        huespedID.setDni(dni);
        huespedID.setTipoDocumento(tipoDocumento);

        Huesped huesped = huespedRepository.findById(huespedID)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Debe crear el huésped antes"
                ));

        PersonaFisica personaFisica = new PersonaFisica();
        personaFisica.setHuesped(huesped);

        PersonaFisica guardada =
                responsablePagoRepository.save(personaFisica);

        return classMapper.toDTO(guardada);
    }

    // ==========================================================
    // ALTA PERSONA JURIDICA
    // ==========================================================

    private ResponsablePagoDTO altaPersonaJuridica(ResponsablePagoDTO dto) {

        StringBuilder errores = new StringBuilder();

        if (dto.getRazonSocial() == null || dto.getRazonSocial().trim().isEmpty()) {
            errores.append("Razón social obligatoria. ");
        }

        if (dto.getCuit() == null || dto.getCuit().trim().isEmpty()) {
            errores.append("CUIT obligatorio. ");
        }

        if (dto.getDireccion() == null) {
            errores.append("Dirección obligatoria. ");
        }

        if (errores.length() > 0) {
            throw new NegocioException(errores.toString().trim());
        }

        if (responsablePagoRepository.findPersonaJuridicaByCuit(dto.getCuit()).isPresent()) {
            throw new NegocioException("El CUIT ya existe");
        }

        Direccion direccion = convertirDireccionDTO(dto.getDireccion());
        Direccion direccionGuardada = direccionRepository.save(direccion);

        PersonaJuridica pj = new PersonaJuridica();
        pj.setCuit(dto.getCuit());
        pj.setRazonSocial(dto.getRazonSocial());
        pj.setDireccionEmpresa(direccionGuardada);

        PersonaJuridica guardada =
                responsablePagoRepository.save(pj);

        return classMapper.toDTO(guardada);
    }

    // ==========================================================
    // MODIFICAR RESPONSABLE DE PAGO (SOLO JURIDICA)
    // ==========================================================

    @Transactional
    public ResponsablePagoDTO modificarResponsablePago(ResponsablePagoDTO dto) {

        if (dto == null) {
            throw new NegocioException("El DTO no puede ser nulo");
        }

        if (dto.getId() == null) {
            throw new NegocioException("Debe indicar el ID del responsable de pago");
        }

        ResponsablePago responsablePago =
                responsablePagoRepository.findById(dto.getId())
                        .orElseThrow(() -> new RecursoNoEncontradoException(
                                "No se encontró responsable de pago con ID: " + dto.getId()
                        ));

        if (responsablePago instanceof PersonaFisica) {
            throw new NegocioException(
                    "No se puede modificar una Persona Física"
            );
        }

        PersonaJuridica pj = (PersonaJuridica) responsablePago;

        if (dto.getCuit() != null && !dto.getCuit().trim().isEmpty()) {

            var existente =
                    responsablePagoRepository.findPersonaJuridicaByCuit(dto.getCuit());

            if (existente.isPresent()
                    && !existente.get().getId().equals(pj.getId())) {
                throw new NegocioException("El CUIT ya está en uso");
            }

            pj.setCuit(dto.getCuit());
        }

        if (dto.getRazonSocial() != null) {
            pj.setRazonSocial(dto.getRazonSocial());
        }

        PersonaJuridica guardada =
                responsablePagoRepository.save(pj);

        return classMapper.toDTO(guardada);
    }

    // ==========================================================
    // ELIMINAR
    // ==========================================================

    @Transactional
    public void eliminarResponsablePago(ResponsablePagoDTO dto) {

        if (dto == null || dto.getId() == null) {
            throw new NegocioException("Debe indicar el ID");
        }

        ResponsablePago responsablePago =
                responsablePagoRepository.findById(dto.getId())
                        .orElseThrow(() -> new RecursoNoEncontradoException(
                                "No se encontró responsable de pago"
                        ));

        List<Factura> facturasAsociadas = facturaRepository.findAll().stream()
                .filter(f -> f.getResponsablePago() != null
                        && f.getResponsablePago().getId().equals(dto.getId()))
                .toList();

        if (!facturasAsociadas.isEmpty()) {
            throw new NegocioException(
                    "No se puede eliminar: tiene facturas asociadas"
            );
        }

        responsablePagoRepository.delete(responsablePago);
    }

    // ==========================================================
    // UTIL
    // ==========================================================

    private Direccion convertirDireccionDTO(DireccionDTO dto) {

        Direccion d = new Direccion();
        d.setCalle(dto.getCalle());
        d.setNumero(dto.getNumero());
        d.setLocalidad(dto.getLocalidad());
        d.setDepartamento(dto.getDepartamento());
        d.setPiso(dto.getPiso());
        d.setCodigoPostal(dto.getCodigoPostal());
        d.setProvincia(dto.getProvincia());
        d.setPais(dto.getPais());

        return d;
    }
}
