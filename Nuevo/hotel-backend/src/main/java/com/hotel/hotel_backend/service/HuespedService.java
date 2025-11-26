package com.hotel.hotel_backend.service;

import com.hotel.hotel_backend.dao.HuespedDAO;
import com.hotel.hotel_backend.domain.Huesped;

import java.util.List;

public class HuespedService {

    private final HuespedDAO huespedDAO;

    public HuespedService(HuespedDAO huespedDAO) {
        this.huespedDAO = huespedDAO;
    }

    public void darAlta(Huesped h) {

        if (huespedDAO.findByNumeroDocumento(h.getNumeroDocumento()).isPresent()) {
            throw new RuntimeException("Ya existe un huésped con ese tipo y número de documento");
        }

        if (h.getCuit() != null && !h.getCuit().isEmpty()) {
            if (huespedDAO.findByCuit(h.getCuit()).isPresent()) {
                throw new RuntimeException("Ya existe un huésped con ese CUIT");
            }
        }

        huespedDAO.save(h);
    }


    public List<Huesped> buscarConFiltros(String apellido, String nombre, String tipoDocumento, String numeroDocumento) {

        // Tomo todos los huéspedes
        List<Huesped> todos = huespedDAO.findAll();

        // SI TODOS los parámetros vienen vacíos → retorno lista completa
        if ((apellido == null || apellido.isEmpty()) &&
            (nombre == null || nombre.isEmpty()) &&
            (tipoDocumento == null || tipoDocumento.isEmpty()) &&
            (numeroDocumento == null || numeroDocumento.isEmpty())) {

            return todos;
        }

        // Sino, filtro según los que estén completos
        return todos.stream()
                .filter(h -> (apellido == null || apellido.isEmpty() ||
                        h.getApellido().startsWith(apellido)))
                .filter(h -> (nombre == null || nombre.isEmpty() ||
                        h.getNombre().startsWith(nombre)))
                .filter(
                    h -> (tipoDocumento == null || tipoDocumento.isEmpty() ||
                        h.getTipoDocumento().equalsIgnoreCase(tipoDocumento))
                    )
                .filter(
                    h -> (numeroDocumento == null || numeroDocumento.isEmpty() ||
                        h.getNumeroDocumento().startsWith(numeroDocumento))
                    )
                .toList();
    }

    public Huesped modificarHuesped(Huesped nuevosDatos) {
        Huesped existente = huespedDAO.findByNumeroDocumento(nuevosDatos.getNumeroDocumento())
        .orElseThrow(() -> new RuntimeException("No existe un huésped con ese documento"));


        // Actualizar todos los campos
        existente.setApellido(nuevosDatos.getApellido());
        existente.setNombre(nuevosDatos.getNombre());
        existente.setTipoDocumento(nuevosDatos.getTipoDocumento());
        existente.setCuit(nuevosDatos.getCuit());
        existente.setPosicionIVA(nuevosDatos.getPosicionIVA());
        existente.setDireccion(nuevosDatos.getDireccion());
        existente.setEmail(nuevosDatos.getEmail());
        existente.setOcupacion(nuevosDatos.getOcupacion());
        existente.setNacionalidad(nuevosDatos.getNacionalidad());
        existente.setDireccionCompleta(nuevosDatos.getDireccion());

        huespedDAO.update(existente);
        return existente;
    }



    public List<Huesped> listar() {
        return huespedDAO.findAll();
    }
}
