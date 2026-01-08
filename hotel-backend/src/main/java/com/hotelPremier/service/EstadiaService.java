package com.hotelPremier.service;

import com.hotelPremier.classes.DTO.EstadiaDTO;
import com.hotelPremier.classes.DTO.HuespedDTO;
import com.hotelPremier.classes.Dominio.Estadia;
import com.hotelPremier.classes.Dominio.Habitacion;
import com.hotelPremier.classes.Dominio.Huesped;
import com.hotelPremier.classes.Dominio.HuespedID;
import com.hotelPremier.classes.Dominio.Reserva;
import com.hotelPremier.classes.Dominio.estadia.observer.ActualizarHabitacionObserver;
import com.hotelPremier.classes.Dominio.estadia.observer.ActualizarReservaObserver;
import com.hotelPremier.classes.Dominio.servicioExtra.ServicioExtra;
import com.hotelPremier.classes.exception.NegocioException;
import com.hotelPremier.classes.exception.RecursoNoEncontradoException;
import com.hotelPremier.classes.mapper.ClassMapper;
import com.hotelPremier.repository.EstadiaRepository;
import com.hotelPremier.repository.HabitacionRepository;
import com.hotelPremier.repository.HuespedRepository;
import com.hotelPremier.repository.ReservaRepository;
import com.hotelPremier.repository.ServicioExtraRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class EstadiaService {

    @Autowired
    private EstadiaRepository estadiaRepository;

    @Autowired
    private ServicioExtraRepository servicioExtraRepository;

    @Autowired
    private HabitacionRepository habitacionRepository;

    @Autowired
    private HuespedRepository huespedRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ClassMapper mapper;

    public List<EstadiaDTO> obtenerEstadiasSinFactura() {
        List<Estadia> lista = estadiaRepository.estadiasSinFactura();
        return mapper.toDTOsEstadia(lista);
    }

    public EstadiaDTO obtenerEstadiaEnCurso(Integer nroHabitacion) {
        Estadia estadia = estadiaRepository.estadiaEnCurso(nroHabitacion);
        if (estadia == null) {
            throw new RecursoNoEncontradoException(
                "No hay estadía en curso para la habitación " + nroHabitacion
            );
        }

        EstadiaDTO dto = mapper.toDTOsEstadia(List.of(estadia)).get(0);

        if (estadia.getId_estadia() != null) {
            List<ServicioExtra> consumos =
                servicioExtraRepository.findByEstadiaId(estadia.getId_estadia());
            dto.setListaconsumos(mapper.toDTOsServicioExtra(consumos));
        }

        return dto;
    }

    public Estadia iniciarEstadia(Estadia estadia) {
        prepararEstadiaParaInicio(estadia);
        estadia.iniciarEstadia();
        estadiaRepository.save(estadia);
        return estadia;
    }

    private void prepararEstadiaParaInicio(Estadia estadia) {
        estadia.registrarObserver(new ActualizarHabitacionObserver());
        estadia.registrarObserver(new ActualizarReservaObserver());
    }

    @Transactional
    public EstadiaDTO agregarEstadia(EstadiaDTO dto) {

        if (dto.getHabitacion() == null || dto.getHabitacion().getNumero() == null) {
            throw new NegocioException("Debe especificar el número de habitación.");
        }

        Integer nroHabitacion = dto.getHabitacion().getNumero();

        Habitacion habitacion = habitacionRepository.findById(nroHabitacion)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe la habitación con número: " + nroHabitacion));

        Estadia estadiaExistente = estadiaRepository.estadiaEnCurso(nroHabitacion);
        if (estadiaExistente != null) {
            throw new NegocioException(
                    "Ya existe una estadía en curso para la habitación " + nroHabitacion);
        }

        if (dto.getCheckin() == null) {
            throw new NegocioException("Debe especificar la fecha de check-in.");
        }

        if (dto.getListahuesped() == null || dto.getListahuesped().isEmpty()) {
            throw new NegocioException("Debe especificar al menos un huésped.");
        }

        List<Huesped> huespedes = new ArrayList<>();
        for (HuespedDTO huespedDTO : dto.getListahuesped()) {

            if (huespedDTO.getHuespedID() == null) {
                throw new NegocioException("Cada huésped debe tener su identificación.");
            }

            String dni = huespedDTO.getHuespedID().getDni();
            String tipoDoc = huespedDTO.getHuespedID().getTipoDocumento();

            HuespedID huespedID = new HuespedID();
            huespedID.setDni(dni);
            huespedID.setTipoDocumento(tipoDoc);

            Huesped huesped = huespedRepository.findById(huespedID)
                    .orElseThrow(() -> new RecursoNoEncontradoException(
                            "No existe el huésped con " + tipoDoc + ": " + dni));

            huespedes.add(huesped);
        }

        Reserva reserva;
        boolean reservaExistente = dto.getReserva() != null && dto.getReserva().getId_reserva() != null;

        if (reservaExistente) {

            Integer idReserva = dto.getReserva().getId_reserva();
            reserva = reservaRepository.findById(idReserva)
                    .orElseThrow(() -> new RecursoNoEncontradoException(
                            "No existe la reserva con ID: " + idReserva));

            if (!"PENDIENTE".equalsIgnoreCase(reserva.getEstado())) {
                throw new NegocioException(
                        "La reserva " + idReserva + " no está en estado PENDIENTE.");
            }

        } else {

            if (dto.getCheckout() == null) {
                throw new NegocioException(
                        "Debe especificar la fecha de check-out cuando no se asocia una reserva.");
            }

            Huesped primerHuesped = huespedes.get(0);
            reserva = new Reserva();
            reserva.setFecha_desde(dto.getCheckin());
            reserva.setFecha_hasta(dto.getCheckout());
            reserva.setNombre(primerHuesped.getNombre());
            reserva.setApellido(primerHuesped.getApellido());
            reserva.setTelefono(primerHuesped.getTelefono());
            reserva.setHabitacion(habitacion);
            reserva.setEstado("FINALIZADA");

            reservaRepository.save(reserva);
        }

        Estadia estadia = new Estadia();

        if (reservaExistente) {
            estadia.setCheckin(reserva.getFecha_desde());
            estadia.setCheckout(reserva.getFecha_hasta());
        } else {
            estadia.setCheckin(dto.getCheckin());
            estadia.setCheckout(dto.getCheckout());
        }

        estadia.setHabitacion(habitacion);
        estadia.setListahuesped(huespedes);
        estadia.setEstado("ENCURSO");

        estadia.setReserva(reserva);
        reserva.setEstadia(estadia);

        estadiaRepository.save(estadia);

        if (reservaExistente) {
            reserva.consumir();
        }
        reservaRepository.save(reserva);

        for (Huesped huesped : huespedes) {
            if (huesped.getListaEstadia() == null) {
                huesped.setListaEstadia(new ArrayList<>());
            }
            huesped.getListaEstadia().add(estadia);
            huespedRepository.save(huesped);
        }

        habitacion.setEstado("OCUPADA");
        habitacionRepository.save(habitacion);

        prepararEstadiaParaInicio(estadia);
        estadia.iniciarEstadia();
        estadiaRepository.save(estadia);

        return mapper.toDTOEstadia(estadia);
    }
}
