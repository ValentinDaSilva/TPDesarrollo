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
        // Convertir DTO a dominio      
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

    public void registrarHuesped(Huesped huesped)
        throws HuespedYaExistenteException, HuespedInvalidoException {

    
        HuespedDTO dto = dao.getHuesped(huesped.getTipoDocumento(), huesped.getNumeroDocumento());
        
        if (dto != null) {
            throw new HuespedYaExistenteException (
                "Ya se encontró un huésped registrado con ese tipo y número de documento."
            );
        }
        HuespedDTO nuevoDto = new HuespedDTO();
        nuevoDto.setApellido(huesped.getApellido());
        nuevoDto.setNombre(huesped.getNombre());
        nuevoDto.setTipoDocumento(huesped.getTipoDocumento());
        nuevoDto.setNumeroDocumento(huesped.getNumeroDocumento());
        nuevoDto.setCuit(huesped.getCuit());
        nuevoDto.setPosicionIVA(huesped.getPosicionIVA());
        nuevoDto.setDireccion(huesped.getDireccion());
        nuevoDto.setTelefono(huesped.getTelefono());
        nuevoDto.setEmail(huesped.getEmail());
        nuevoDto.setOcupacion(huesped.getOcupacion());
        nuevoDto.setNacionalidad(huesped.getNacionalidad());
        
        dao.putHuesped(nuevoDto);

        return;
    }

    public void modificarHuesped(Huesped husped){
        return ;
    }
}
