package com.hotelPremier.service;

import com.hotelPremier.classes.DTO.FacturaDTO;
import com.hotelPremier.classes.Dominio.Estadia;
import com.hotelPremier.classes.Dominio.Factura;
import com.hotelPremier.classes.Dominio.factura.calculo.CalculoFacturaStrategy;
import com.hotelPremier.classes.Dominio.factura.calculo.DatosFactura;
import com.hotelPremier.classes.Dominio.factura.calculo.SelectorEstrategiaCalculo;
import com.hotelPremier.classes.Dominio.factura.observer.CheckoutFacturaObserver;
import com.hotelPremier.classes.Dominio.factura.observer.NotaCreditoFacturaObserver;
import com.hotelPremier.classes.Dominio.factura.observer.PagoFacturaObserver;
import com.hotelPremier.classes.Dominio.responsablePago.PersonaFisica;
import com.hotelPremier.classes.Dominio.responsablePago.PersonaJuridica;
import com.hotelPremier.classes.Dominio.responsablePago.ResponsablePago;
import com.hotelPremier.classes.Dominio.servicioExtra.ServicioExtra;
import com.hotelPremier.classes.exception.RecursoNoEncontradoException;
import com.hotelPremier.repository.EstadiaRepository;
import com.hotelPremier.repository.FacturaRepository;
import com.hotelPremier.repository.HabitacionRepository;
import com.hotelPremier.repository.ResponsablePagoRepository;
import com.hotelPremier.repository.ServicioExtraRepository;
import com.hotelPremier.classes.mapper.ClassMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private EstadiaRepository estadiaRepository;

    @Autowired
    private ResponsablePagoRepository responsablePagoRepository;

    @Autowired
    private ServicioExtraRepository servicioExtraRepository;

    @Autowired
    private HabitacionRepository habitacionRepository;

    @Autowired
    private ClassMapper mapper;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Obtiene las facturas de estadías finalizadas para una habitación.
     */
    public List<FacturaDTO> obtenerFacturasPorHabitacion(Integer nroHabitacion) {

        if (habitacionRepository.findByNumero(nroHabitacion) == null) {
            throw new RecursoNoEncontradoException(
                    "Habitación no encontrada con número: " + nroHabitacion
            );
        }

        List<Factura> lista = facturaRepository.findAll().stream()
                .filter(f -> f.getEstadia() != null &&
                             f.getEstadia().getHabitacion() != null &&
                             "FINALIZADA".equals(f.getEstadia().getEstado()) &&
                             f.getEstadia().getHabitacion().getNumero().equals(nroHabitacion))
                .toList();

        return lista.stream()
                .map(mapper::toDTOFactura)
                .toList();
    }

    /**
     * Busca facturas asociadas a un DNI.
     */
    public List<FacturaDTO> buscarPorDni(String dni) {
        return facturaRepository.buscarPorDni(dni).stream()
                .map(mapper::toDTOFactura)
                .toList();
    }

    /**
     * Filtra facturas por CUIT, tipo de documento o número de documento.
     */
    public List<FacturaDTO> filtrarFacturas(
            String cuit,
            String tipoDocumento,
            String numeroDocumento
    ) {

        return facturaRepository.findAll().stream()
                .filter(f -> coincideCuit(f, cuit))
                .filter(f -> coincideTipoDoc(f, tipoDocumento))
                .filter(f -> coincideNumeroDoc(f, numeroDocumento))
                .map(mapper::toDTOFactura)
                .toList();
    }

    /**
     * Crea una factura calculando el total con Strategy.
     */
    @Transactional
    public FacturaDTO crearFacturaConCalculo(FacturaDTO dto, DatosFactura datosFactura) {

        if (dto.getEstadia() == null || dto.getEstadia().getID() == null) {
            throw new IllegalArgumentException("La factura debe tener una estadía asociada");
        }

        Estadia estadia = estadiaRepository.findById(dto.getEstadia().getID())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Estadía no encontrada con ID: " + dto.getEstadia().getID()));

        // ✔ Actualizar checkout usando SOLO LocalDate
        if (datosFactura.getFechaHoraCheckoutReal() != null) {
            estadia.setCheckout(
                    datosFactura.getFechaHoraCheckoutReal().toLocalDate()
            );
        }

        Factura factura = mapper.toEntityFactura(dto);

        estadia.generarFactura(factura);
        estadia.finalizar();

        CalculoFacturaStrategy estrategia =
                SelectorEstrategiaCalculo.seleccionarEstrategia(estadia, datosFactura);

        BigDecimal totalCalculado = estrategia.calcularTotal(estadia, datosFactura);

        factura.setTotal(totalCalculado.floatValue());
        factura.setEstadia(estadia);
        factura.setFecha(LocalDate.now());

        if (dto.getResponsablepago() != null &&
            dto.getResponsablepago().getId() != null &&
            dto.getResponsablepago().getId() > 0) {

            // Cargar el ResponsablePago y asegurar que esté completamente gestionado
            // Usar findById para cargar la entidad completa y asegurar que esté en el contexto
            ResponsablePago rp = responsablePagoRepository.findById(
                    dto.getResponsablepago().getId())
                    .orElseThrow(() -> new RecursoNoEncontradoException(
                            "Responsable de pago no encontrado con ID: " +
                            dto.getResponsablepago().getId()));

            // Asegurar que la entidad esté gestionada en el contexto de persistencia
            // Si no está gestionada, hacer merge para que esté gestionada
            if (!entityManager.contains(rp)) {
                rp = entityManager.merge(rp);
            }
            
            // IMPORTANTE: No tocar la Direccion asociada para evitar que JPA intente persistirla
            // y cause que se cree un nuevo ResponsablePago. Solo asignar la referencia.

            factura.setResponsablePago(rp);
        }

        estadiaRepository.save(estadia);
        facturaRepository.save(factura);

        FacturaDTO facturaDTO = mapper.toDTOFactura(factura);
        facturaDTO.setFechaHoraCheckoutReal(null);
        facturaDTO.setTotalEstimado(null);

        if (facturaDTO.getConsumosIds() == null) {
            facturaDTO.setConsumosIds(new ArrayList<>());
        }

        return facturaDTO;
    }

    /**
     * Crea una factura (si tiene estadía, siempre usa cálculo automático).
     */
    @Transactional
    public FacturaDTO crearFactura(FacturaDTO dto) {
        if (dto.getEstadia() != null && dto.getEstadia().getID() != null) {
            return crearFacturaConCalculoAutomatico(dto);
        }

        Factura factura = mapper.toEntityFactura(dto);
        factura.setFecha(LocalDate.now());

        if (dto.getResponsablepago() != null &&
            dto.getResponsablepago().getId() != null &&
            dto.getResponsablepago().getId() > 0) {

            // Cargar el ResponsablePago y asegurar que esté completamente gestionado
            // Usar findById para cargar la entidad completa y asegurar que esté en el contexto
            ResponsablePago rp = responsablePagoRepository.findById(
                    dto.getResponsablepago().getId())
                    .orElseThrow(() -> new RecursoNoEncontradoException(
                            "Responsable de pago no encontrado con ID: " +
                            dto.getResponsablepago().getId()));

            // Asegurar que la entidad esté gestionada en el contexto de persistencia
            // Si no está gestionada, hacer merge para que esté gestionada
            if (!entityManager.contains(rp)) {
                rp = entityManager.merge(rp);
            }
            
            // IMPORTANTE: No tocar la Direccion asociada para evitar que JPA intente persistirla
            // y cause que se cree un nuevo ResponsablePago. Solo asignar la referencia.

            factura.setResponsablePago(rp);
        }

        facturaRepository.save(factura);
        return mapper.toDTOFactura(factura);
    }

    private FacturaDTO crearFacturaConCalculoAutomatico(FacturaDTO dto) {

        DatosFactura datosFactura = new DatosFactura();

        List<ServicioExtra> consumos = new ArrayList<>();
        if (dto.getConsumosIds() != null &&
            !dto.getConsumosIds().isEmpty() &&
            dto.getEstadia() != null) {

            consumos = servicioExtraRepository.findByEstadiaIdAndServicioIds(
                    dto.getEstadia().getID(),
                    dto.getConsumosIds()
            );
        }

        datosFactura.setConsumosSeleccionados(consumos);
        datosFactura.setTipoFactura(dto.getTipo());
        datosFactura.setFechaHoraCheckoutReal(dto.getFechaHoraCheckoutReal());

        return crearFacturaConCalculo(dto, datosFactura);
    }

    public FacturaDTO generarFacturaFinal(Integer facturaId) {

        Factura factura = facturaRepository.findById(facturaId)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Factura no encontrada con ID: " + facturaId));

        prepararFacturaParaCheckout(factura);
        factura.generarFacturaFinal();
        facturaRepository.save(factura);

        if (factura.getEstadia() != null) {
            estadiaRepository.save(factura.getEstadia());
            if (factura.getEstadia().getHabitacion() != null) {
                habitacionRepository.save(factura.getEstadia().getHabitacion());
            }
        }

        return mapper.toDTOFactura(factura);
    }

    public FacturaDTO pagarFactura(Integer facturaId) {

        Factura factura = facturaRepository.findById(facturaId)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Factura no encontrada con ID: " + facturaId));

        prepararFacturaParaPago(factura);
        factura.pagar();
        facturaRepository.save(factura);

        return mapper.toDTOFactura(factura);
    }

    public FacturaDTO aplicarNotaCreditoAFactura(Integer facturaId) {

        Factura factura = facturaRepository.findById(facturaId)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Factura no encontrada con ID: " + facturaId));

        prepararFacturaParaNotaCredito(factura);
        factura.aplicarNotaCredito();
        facturaRepository.save(factura);

        return mapper.toDTOFactura(factura);
    }

    // =========================
    // FILTROS
    // =========================

    private boolean coincideCuit(Factura f, String cuit) {
        if (cuit == null || cuit.isBlank()) return true;
        if (f.getResponsablePago() instanceof PersonaJuridica pj) {
            return cuit.equals(pj.getCuit());
        }
        return false;
    }

    private boolean coincideTipoDoc(Factura f, String tipoDoc) {
        if (tipoDoc == null || tipoDoc.isBlank()) return true;
        if (f.getResponsablePago() instanceof PersonaFisica pf) {
            return pf.getHuesped()
                     .getHuespedID()
                     .getTipoDocumento()
                     .equalsIgnoreCase(tipoDoc);
        }
        return false;
    }

    private boolean coincideNumeroDoc(Factura f, String numeroDoc) {
        if (numeroDoc == null || numeroDoc.isBlank()) return true;
        if (f.getResponsablePago() instanceof PersonaFisica pf) {
            return pf.getHuesped()
                     .getHuespedID()
                     .getDni()
                     .equalsIgnoreCase(numeroDoc);
        }
        return false;
    }

    // =========================
    // OBSERVERS
    // =========================

    private void prepararFacturaParaCheckout(Factura factura) {
        factura.registrarObserver(new CheckoutFacturaObserver());
    }

    private void prepararFacturaParaPago(Factura factura) {
        factura.registrarObserver(new PagoFacturaObserver());
    }

    private void prepararFacturaParaNotaCredito(Factura factura) {
        factura.registrarObserver(new NotaCreditoFacturaObserver());
    }
}
