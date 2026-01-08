package com.hotelPremier.service;

import com.hotelPremier.classes.DTO.DireccionDTO;
import com.hotelPremier.classes.DTO.ResponsablePagoDTO;
import com.hotelPremier.classes.exception.NegocioException;
import com.hotelPremier.classes.exception.RecursoNoEncontradoException;
import com.hotelPremier.repository.DireccionRepository;
import com.hotelPremier.repository.HuespedRepository;
import com.hotelPremier.repository.ResponsablePagoRepository;
import com.hotelPremier.classes.Dominio.Direccion;
import com.hotelPremier.classes.Dominio.Huesped;
import com.hotelPremier.classes.Dominio.HuespedID;
import com.hotelPremier.classes.Dominio.responsablePago.PersonaFisica;
import com.hotelPremier.classes.Dominio.responsablePago.PersonaJuridica;
import com.hotelPremier.classes.Dominio.responsablePago.ResponsablePago;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResponsablePagoService {

    @Autowired
    private ResponsablePagoRepository responsablePagoRepository;

    @Autowired
    private HuespedRepository huespedRepository;

    @Autowired
    private DireccionRepository direccionRepository;

    /**
     * Record para devolver el resultado de la búsqueda de responsable de pago.
     * Incluye el ID y la razón social (solo para PersonaJuridica).
     */
    public record ResponsablePagoResult(Integer id, String razonSocial) {}

    /**
     * Busca el ID de un ResponsablePago.
     * Si viene CUIT, busca en PersonaJuridica.
     * Si no viene CUIT pero viene DNI y tipoDocumento, busca en PersonaFisica.
     * Si no encuentra un PersonaFisica, lo crea automáticamente asociado al Huesped.
     * 
     * @param dni DNI del huésped (opcional si viene CUIT)
     * @param tipoDocumento Tipo de documento (opcional si viene CUIT)
     * @param cuit CUIT de la persona jurídica (opcional)
     * @return ResponsablePagoResult con el ID y razón social (si aplica)
     * @throws IllegalArgumentException si no se proporcionan los datos necesarios
     * @throws RecursoNoEncontradoException si no se encuentra el responsable de pago (para CUIT) o el huésped (para DNI)
     */
    @Transactional
    public ResponsablePagoResult buscarResponsablePago(String dni, String tipoDocumento, String cuit) {
        
        // Si viene CUIT, buscar en PersonaJuridica
        if (cuit != null && !cuit.trim().isEmpty()) {
            PersonaJuridica pj = responsablePagoRepository.findPersonaJuridicaByCuit(cuit)
                    .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No se encontró responsable de pago con CUIT: " + cuit
                    ));
            return new ResponsablePagoResult(pj.getId(), pj.getRazonSocial());
        }
        
        // Si no viene CUIT, buscar en PersonaFisica por DNI y tipoDocumento
        if (dni != null && !dni.trim().isEmpty() && 
            tipoDocumento != null && !tipoDocumento.trim().isEmpty()) {
            
            // Buscar PersonaFisica existente
            var personaFisicaOpt = responsablePagoRepository.findPersonaFisicaByDniAndTipoDocumento(dni, tipoDocumento);
            
            if (personaFisicaOpt.isPresent()) {
                PersonaFisica pf = personaFisicaOpt.get();
                return new ResponsablePagoResult(pf.getId(), null);
            }
            
            // Si no existe, buscar el Huesped para crear el PersonaFisica
            HuespedID huespedID = new HuespedID();
            huespedID.setDni(dni);
            huespedID.setTipoDocumento(tipoDocumento);
            
            Huesped huesped = huespedRepository.findById(huespedID)
                    .orElseThrow(() -> new RecursoNoEncontradoException(
                        String.format("No se encontró huésped con DNI: %s y tipo de documento: %s. Debe crear el huésped primero.", dni, tipoDocumento)
                    ));
            
            // Crear PersonaFisica asociada al Huesped
            PersonaFisica nuevaPersonaFisica = new PersonaFisica();
            nuevaPersonaFisica.setHuesped(huesped);
            
            PersonaFisica personaFisicaGuardada = responsablePagoRepository.save(nuevaPersonaFisica);
            return new ResponsablePagoResult(personaFisicaGuardada.getId(), null);
        }
        
        // Si no se proporcionan los datos necesarios
        throw new IllegalArgumentException(
            "Debe proporcionar CUIT o bien DNI y tipoDocumento para buscar el responsable de pago"
        );
    }

    /**
     * Da de alta un nuevo responsable de pago (PersonaFisica o PersonaJuridica).
     * 
     * @param dto DTO con los datos del responsable de pago
     * @return ResponsablePagoResult con el ID y razón social (si aplica)
     * @throws NegocioException si faltan datos obligatorios o ya existe
     */
    @Transactional
    public ResponsablePagoResult altaResponsablePago(ResponsablePagoDTO dto) {
        if (dto == null) {
            throw new NegocioException("El DTO del responsable de pago no puede ser nulo");
        }

        String tipo = dto.getTipo();
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new NegocioException("Debe especificar el tipo de responsable de pago (FISICA o JURIDICA)");
        }

        tipo = tipo.toUpperCase().trim();

        if ("FISICA".equals(tipo)) {
            return altaPersonaFisica(dto);
        } else if ("JURIDICA".equals(tipo)) {
            return altaPersonaJuridica(dto);
        } else {
            throw new NegocioException("El tipo de responsable de pago debe ser FISICA o JURIDICA");
        }
    }

    /**
     * Da de alta una PersonaFisica.
     */
    private ResponsablePagoResult altaPersonaFisica(ResponsablePagoDTO dto) {
        String dni = dto.getDni();
        String tipoDocumento = dto.getTipoDocumento();

        if (dni == null || dni.trim().isEmpty()) {
            throw new NegocioException("Debe especificar el DNI para PersonaFisica");
        }
        if (tipoDocumento == null || tipoDocumento.trim().isEmpty()) {
            throw new NegocioException("Debe especificar el tipo de documento para PersonaFisica");
        }

        // Verificar si ya existe
        var personaFisicaOpt = responsablePagoRepository.findPersonaFisicaByDniAndTipoDocumento(dni, tipoDocumento);
        if (personaFisicaOpt.isPresent()) {
            throw new NegocioException(
                String.format("Ya existe un responsable de pago PersonaFisica con DNI: %s y tipo de documento: %s", dni, tipoDocumento)
            );
        }

        // Buscar el Huesped
        HuespedID huespedID = new HuespedID();
        huespedID.setDni(dni);
        huespedID.setTipoDocumento(tipoDocumento);

        Huesped huesped = huespedRepository.findById(huespedID)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                    String.format("No se encontró huésped con DNI: %s y tipo de documento: %s. Debe crear el huésped primero.", dni, tipoDocumento)
                ));

        // Crear PersonaFisica
        PersonaFisica personaFisica = new PersonaFisica();
        personaFisica.setHuesped(huesped);

        PersonaFisica personaFisicaGuardada = responsablePagoRepository.save(personaFisica);
        return new ResponsablePagoResult(personaFisicaGuardada.getId(), null);
    }

    /**
     * Da de alta una PersonaJuridica.
     * Valida todos los campos obligatorios según el caso de uso CU12.
     * La validación de campos faltantes se realiza ANTES y en forma INDEPENDIENTE
     * de la validación del CUIT duplicado.
     */
    private ResponsablePagoResult altaPersonaJuridica(ResponsablePagoDTO dto) {
        // PASO 2.A: Validar campos obligatorios (ANTES y en forma INDEPENDIENTE de la validación del CUIT)
        StringBuilder errores = new StringBuilder();
        
        String razonSocial = dto.getRazonSocial();
        if (razonSocial == null || razonSocial.trim().isEmpty()) {
            errores.append("Razón social es obligatoria. ");
        }
        
        String cuit = dto.getCuit();
        if (cuit == null || cuit.trim().isEmpty()) {
            errores.append("CUIT es obligatorio. ");
        }
        
        String telefono = dto.getTelefono();
        if (telefono == null || telefono.trim().isEmpty()) {
            errores.append("Teléfono es obligatorio. ");
        }
        
        DireccionDTO direccionDTO = dto.getDireccion();
        if (direccionDTO == null) {
            errores.append("Dirección es obligatoria. ");
        } else {
            // Validar campos obligatorios de la dirección
            if (direccionDTO.getCalle() == null || direccionDTO.getCalle().trim().isEmpty()) {
                errores.append("Calle es obligatoria. ");
            }
            if (direccionDTO.getNumero() == null) {
                errores.append("Número es obligatorio. ");
            }
            if (direccionDTO.getLocalidad() == null || direccionDTO.getLocalidad().trim().isEmpty()) {
                errores.append("Localidad es obligatoria. ");
            }
            if (direccionDTO.getCodigoPostal() == null) {
                errores.append("Código postal es obligatorio. ");
            }
            if (direccionDTO.getProvincia() == null || direccionDTO.getProvincia().trim().isEmpty()) {
                errores.append("Provincia es obligatoria. ");
            }
            if (direccionDTO.getPais() == null || direccionDTO.getPais().trim().isEmpty()) {
                errores.append("País es obligatorio. ");
            }
            // departamento y piso son opcionales según el caso de uso
        }
        
        // Si hay errores de validación, lanzar excepción con todos los errores
        if (errores.length() > 0) {
            throw new NegocioException(errores.toString().trim());
        }
        
        // PASO 2.B: Validar que el CUIT no exista (validación independiente según el caso de uso)
        var personaJuridicaOpt = responsablePagoRepository.findPersonaJuridicaByCuit(cuit);
        if (personaJuridicaOpt.isPresent()) {
            throw new NegocioException("¡CUIDADO! El CUIT ya existe en el sistema");
        }

        // Convertir DireccionDTO a Direccion
        Direccion direccion = convertirDireccionDTO(direccionDTO);
        
        // Guardar la dirección primero
        Direccion direccionGuardada = direccionRepository.save(direccion);

        // Crear PersonaJuridica
        PersonaJuridica personaJuridica = new PersonaJuridica();
        personaJuridica.setCuit(cuit);
        personaJuridica.setRazonSocial(razonSocial);
        personaJuridica.setDireccionEmpresa(direccionGuardada);
        // Nota: El teléfono no está en la entidad PersonaJuridica según el modelo actual
        // Si se necesita almacenar, habría que agregarlo a la entidad

        PersonaJuridica personaJuridicaGuardada = responsablePagoRepository.save(personaJuridica);
        return new ResponsablePagoResult(personaJuridicaGuardada.getId(), personaJuridicaGuardada.getRazonSocial());
    }

    /**
     * Convierte un DireccionDTO a Direccion.
     */
    private Direccion convertirDireccionDTO(DireccionDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Direccion direccion = new Direccion();
        direccion.setCalle(dto.getCalle());
        direccion.setNumero(dto.getNumero());
        direccion.setLocalidad(dto.getLocalidad());
        direccion.setDepartamento(dto.getDepartamento());
        direccion.setPiso(dto.getPiso());
        direccion.setCodigoPostal(dto.getCodigoPostal());
        direccion.setProvincia(dto.getProvincia());
        direccion.setPais(dto.getPais());
        
        return direccion;
    }

    /**
     * Modifica un responsable de pago existente.
     * 
     * @param dto DTO con los datos del responsable de pago a modificar (debe incluir el ID)
     * @return ResponsablePagoResult con el ID y razón social (si aplica)
     * @throws NegocioException si faltan datos obligatorios
     * @throws RecursoNoEncontradoException si no se encuentra el responsable de pago
     */
    @Transactional
    public ResponsablePagoResult modificarResponsablePago(ResponsablePagoDTO dto) {
        if (dto == null) {
            throw new NegocioException("El DTO del responsable de pago no puede ser nulo");
        }

        if (dto.getId() == null) {
            throw new NegocioException("Debe especificar el ID del responsable de pago a modificar");
        }

        ResponsablePago responsablePago = responsablePagoRepository.findById(dto.getId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                    String.format("No se encontró responsable de pago con ID: %d", dto.getId())
                ));

        if (responsablePago instanceof PersonaFisica) {
            throw new NegocioException("No se puede modificar una PersonaFisica. La relación se mantiene con el huésped.");
        } else if (responsablePago instanceof PersonaJuridica) {
            return modificarPersonaJuridica((PersonaJuridica) responsablePago, dto);
        } else {
            throw new NegocioException("Tipo de responsable de pago no reconocido");
        }
    }

    /**
     * Modifica una PersonaJuridica.
     */
    private ResponsablePagoResult modificarPersonaJuridica(PersonaJuridica personaJuridica, ResponsablePagoDTO dto) {
        String cuit = dto.getCuit();
        String razonSocial = dto.getRazonSocial();

        if (cuit != null && !cuit.trim().isEmpty()) {
            // Verificar que el CUIT no esté en uso por otro responsable de pago
            var personaJuridicaOpt = responsablePagoRepository.findPersonaJuridicaByCuit(cuit);
            if (personaJuridicaOpt.isPresent() && !personaJuridicaOpt.get().getId().equals(personaJuridica.getId())) {
                throw new NegocioException(
                    String.format("Ya existe otro responsable de pago PersonaJuridica con CUIT: %s", cuit)
                );
            }
            personaJuridica.setCuit(cuit);
        }

        if (razonSocial != null) {
            personaJuridica.setRazonSocial(razonSocial);
        }

        PersonaJuridica personaJuridicaGuardada = responsablePagoRepository.save(personaJuridica);
        return new ResponsablePagoResult(personaJuridicaGuardada.getId(), personaJuridicaGuardada.getRazonSocial());
    }
}
