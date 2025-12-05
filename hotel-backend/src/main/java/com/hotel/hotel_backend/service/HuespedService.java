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
        if (h.getNumeroDocumento() != null && !h.getNumeroDocumento().isEmpty()) {
            boolean existeDocumento = huespedDAO.existsByNumeroDocumento(h.getNumeroDocumento());
            
            if (existeDocumento && !dto.isForzar()) {
                throw new RuntimeException("DNI_DUPLICADO");
            }
        }

        huespedDAO.save(h);

        return MapearADTO.mapearHuesped(h);
    }


    // ==========================================================
    //                   MODIFICAR HUESPED
    // ==========================================================
    public HuespedDTO modificarHuesped(HuespedDTO dto) {

        if (dto.getId() == null) {
            throw new RuntimeException("Debe enviar ID del huésped a modificar.");
        }

        Huesped existente = huespedDAO.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("No existe un huésped con ese ID."));

        Huesped nuevosDatos = MapearADominio.mapearHuesped(dto);

        existente.setNumeroDocumento(nuevosDatos.getNumeroDocumento());
        existente.setApellido(nuevosDatos.getApellido());
        existente.setNombre(nuevosDatos.getNombre());
        existente.setTipoDocumento(nuevosDatos.getTipoDocumento());
        existente.setCuit(nuevosDatos.getCuit());
        existente.setDireccion(nuevosDatos.getDireccion());
        existente.setEmail(nuevosDatos.getEmail());
        existente.setOcupacion(nuevosDatos.getOcupacion());
        existente.setNacionalidad(nuevosDatos.getNacionalidad());
        existente.setTelefono(nuevosDatos.getTelefono());
        existente.setFechaNacimiento(nuevosDatos.getFechaNacimiento());

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
    public void eliminarHuesped(HuespedDTO dto) {
        if (dto.getId() == null) {
            throw new RuntimeException("Debe enviar ID del huésped a eliminar.");
        }

        Huesped h = huespedDAO.findById(dto.getId())
                .orElseThrow(() ->
                        new RuntimeException("No existe un huésped con ese ID."));

        // Validar confirmación con booleano forzar
        if (!dto.isForzar()) {
            throw new RuntimeException("CONFIRMAR_ELIMINACION");
        }

        huespedDAO.delete(h);
    }
}
