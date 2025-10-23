package Gestores;

import ClasesDeDominio.Huesped;
import ClasesDTO.HuespedDTO;
import ClasesDAO.DAOFactory;
import ClasesDAO.HuespedDAO;
import Excepciones.HuespedYaExistenteException;
import Excepciones.HuespedInvalidoException;
import Excepciones.HuespedNoEncontradoException;

/**
 * Gestor encargado de manejar las operaciones de negocio
 * relacionadas con los huéspedes.
 *
 * Interactúa con el HuespedDAO (obtenido a través de DAOFactory)
 * para realizar las operaciones de búsqueda, alta, modificación
 * y eliminación sobre los huéspedes del sistema.
 */
public class GestorHuesped {

    private final HuespedDAO dao;

    public GestorHuesped() {
        this.dao = DAOFactory.getInstancia().getHuespedDAO();
    }

    public Huesped buscarHuesped(String tipoDoc, String nroDoc)
            throws HuespedNoEncontradoException {
        HuespedDTO dto = dao.getHuesped(tipoDoc, nroDoc);
        if (dto == null) {
            throw new HuespedNoEncontradoException(
                "No se encontró ningún huésped con ese documento."
            );
        }
        return convertirAClaseDominio(dto);
    }

    public void registrarHuesped(Huesped huesped)
            throws HuespedYaExistenteException, HuespedInvalidoException {

        HuespedDTO dtoExistente = dao.getHuesped(
            huesped.getTipoDocumento(),
            huesped.getNumeroDocumento()
        );

        if (dtoExistente != null) {
            throw new HuespedYaExistenteException(
                "Ya existe un huésped con ese tipo y número de documento."
            );
        }

        if (huesped.getNombre() == null || huesped.getApellido() == null) {
            throw new HuespedInvalidoException("El huésped debe tener nombre y apellido.");
        }

        dao.putHuesped(convertirADTO(huesped));
    }

    public void modificarHuesped(Huesped huesped) {
        dao.updateHuesped(convertirADTO(huesped));
    }

    public void eliminarHuesped(Huesped huesped) {
        dao.deleteHuesped(convertirADTO(huesped));
    }

    private HuespedDTO convertirADTO(Huesped huesped) {
        HuespedDTO dto = new HuespedDTO();
        dto.setApellido(huesped.getApellido());
        dto.setNombre(huesped.getNombre());
        dto.setTipoDocumento(huesped.getTipoDocumento());
        dto.setNumeroDocumento(huesped.getNumeroDocumento());
        dto.setCuit(huesped.getCuit());
        dto.setPosicionIVA(huesped.getPosicionIVA());
        dto.setDireccion(huesped.getDireccion());
        dto.setTelefono(huesped.getTelefono());
        dto.setEmail(huesped.getEmail());
        dto.setOcupacion(huesped.getOcupacion());
        dto.setNacionalidad(huesped.getNacionalidad());
        return dto;
    }

    private Huesped convertirAClaseDominio(HuespedDTO dto) {
        Huesped h = new Huesped();
        h.setApellido(dto.getApellido());
        h.setNombre(dto.getNombre());
        h.setTipoDocumento(dto.getTipoDocumento());
        h.setNumeroDocumento(dto.getNumeroDocumento());
        h.setCuit(dto.getCuit());
        h.setPosicionIVA(dto.getPosicionIVA());
        h.setDireccion(dto.getDireccion());
        h.setTelefono(dto.getTelefono());
        h.setEmail(dto.getEmail());
        h.setOcupacion(dto.getOcupacion());
        h.setNacionalidad(dto.getNacionalidad());
        h.setDireccionCompleta(dto.getDireccion());
        return h;
    }
}
