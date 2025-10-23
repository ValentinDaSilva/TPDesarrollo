package Gestores;

import ClasesDeDominio.Huesped;
import ClasesDTO.HuespedDTO;
import ClasesDAO.HuespedDAO;
import ClasesDAO.HuespedDAOImp;
import Excepciones.HuespedYaExistenteException;
import Excepciones.HuespedInvalidoException;
import Excepciones.HuespedNoEncontradoException;

public class GestorHuesped {
    private HuespedDAO dao = new HuespedDAOImp();

    public Huesped buscarHuesped(String tipoDoc, String nroDoc) throws HuespedNoEncontradoException {

        HuespedDTO dto = dao.getHuesped(tipoDoc, nroDoc);
        if (dto == null) {
            throw new HuespedNoEncontradoException("No se encontró ningún huésped con ese documento.");
        }
        Huesped h = convertirAClaseDominio(dto);
        return h;
    }

    public void registrarHuesped(Huesped huesped) throws HuespedYaExistenteException, HuespedInvalidoException {
        HuespedDTO dto = dao.getHuesped(huesped.getTipoDocumento(), huesped.getNumeroDocumento());
        
        if (dto != null) {
            throw new HuespedYaExistenteException (
                "Ya se encontró un huésped registrado con ese tipo y número de documento."
            );
        }
        HuespedDTO nuevoDto = convertirADTO(huesped);
        dao.putHuesped(nuevoDto);

        return;
    }

    public void modificarHuesped(Huesped husped){
        HuespedDTO dto = convertirADTO(husped);
        dao.updateHuesped(dto);
        return ;
    }

    public void eliminarHuesped(Huesped huesped) {
        HuespedDTO dto = convertirADTO(huesped);
        dao.deleteHuesped(dto);
        return ;
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
