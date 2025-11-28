// [src/main/java/com/hotel/hotel_backend/service/HuespedService.java]
package com.hotel.hotel_backend.service;

import com.hotel.hotel_backend.dao.HuespedDAO;
import com.hotel.hotel_backend.domain.Huesped;
import com.hotel.hotel_backend.dto.HuespedDTO;
import com.hotel.hotel_backend.service.Mapeo.MapearADominio;
import com.hotel.hotel_backend.service.Mapeo.MapearADTO;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HuespedService {

    private final HuespedDAO huespedDAO;

    public HuespedService(HuespedDAO huespedDAO) {
        this.huespedDAO = huespedDAO;
    }

    // ==========================================================
    //                    CREAR HUESPED
    // ==========================================================
    public HuespedDTO crearHuesped(HuespedDTO dto) {

        Huesped h = MapearADominio.mapearHuesped(dto);

        // Validar documento duplicado
        if (huespedDAO.existsByNumeroDocumento(h.getNumeroDocumento())) {
            throw new RuntimeException("Ya existe un huésped con ese número de documento.");
        }

        // Validar CUIT duplicado
        if (h.getCuit() != null && !h.getCuit().isEmpty()) {
            List<Huesped> coincidencias = huespedDAO.findAllByCuit(h.getCuit());

            if (!dto.isForzar() && !coincidencias.isEmpty()) {
                throw new RuntimeException("CUIT_DUPLICADO");
            }


        }

        huespedDAO.save(h);

        return MapearADTO.mapearHuesped(h);
    }


    // ==========================================================
    //                   MODIFICAR HUESPED
    // ==========================================================
    public HuespedDTO modificarHuesped(HuespedDTO dto) {

        if (dto.getNumeroDocumento() == null) {
            throw new RuntimeException("Debe enviar documento del huésped a modificar.");
        }

        Huesped existente = huespedDAO.findByNumeroDocumento(dto.getNumeroDocumento())
                .orElseThrow(() -> new RuntimeException("No existe un huésped con ese documento."));

        Huesped nuevosDatos = MapearADominio.mapearHuesped(dto);

        existente.setApellido(nuevosDatos.getApellido());
        existente.setNombre(nuevosDatos.getNombre());
        existente.setTipoDocumento(nuevosDatos.getTipoDocumento());
        existente.setCuit(nuevosDatos.getCuit());
        existente.setDireccion(nuevosDatos.getDireccion());
        existente.setEmail(nuevosDatos.getEmail());
        existente.setOcupacion(nuevosDatos.getOcupacion());
        existente.setNacionalidad(nuevosDatos.getNacionalidad());

        huespedDAO.save(existente);

        return MapearADTO.mapearHuesped(existente);
    }

    // ==========================================================
    //                   BUSCAR UNO
    // ==========================================================
    public HuespedDTO buscarPorDocumento(String documento) {
        Huesped h = huespedDAO.findByNumeroDocumento(documento)
                .orElseThrow(() ->
                        new RuntimeException("No existe un huésped con ese documento."));
        return MapearADTO.mapearHuesped(h);
    }

    // ==========================================================
    //                 BUSCAR CON FILTROS
    // ==========================================================
    public List<HuespedDTO> buscarConFiltros(String apellido, String nombre,
                                             String tipoDocumento, String numeroDocumento) {

        return huespedDAO.buscarConFiltros(apellido, nombre, tipoDocumento, numeroDocumento)
                .stream()
                .map(MapearADTO::mapearHuesped)
                .toList();
    }

    // ==========================================================
    //                   LISTAR TODOS
    // ==========================================================
    public List<HuespedDTO> listarTodos() {
        return huespedDAO.findAll()
                .stream()
                .map(MapearADTO::mapearHuesped)
                .toList();
    }

    // ==========================================================
    //                     ELIMINAR
    // ==========================================================
    public void eliminarHuesped(String documento) {
        Huesped h = huespedDAO.findByNumeroDocumento(documento)
                .orElseThrow(() ->
                        new RuntimeException("No existe un huésped con ese documento."));
        huespedDAO.delete(h);
    }
}
