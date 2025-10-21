package Gestores;

import ClasesDeDominio.Huesped;
import ClasesDTO.HuespedDTO;
import ClasesDAO.HuespedDAO;
import ClasesDAO.HuespedDAOImp;
import Excepciones.HuespedNoEncontradoException;
import Excepciones.HuespedInvalidoException;

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

    public Huesped registrarHuesped(String tipoDoc, String nroDoc)
        throws HuespedNoEncontradoException, HuespedInvalidoException {

    
        HuespedDTO dto = dao.getHuesped(tipoDoc, nroDoc);
        
        if (dto == null) {
            throw new HuespedNoEncontradoException(
                "No se encontró ningún huésped con ese documento."
            );
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

        // Opcional: validaciones internas del dominio
        try {
            h.validar();
        } catch (IllegalArgumentException e) {
            throw new HuespedInvalidoException("Datos de huésped inválidos: " + e.getMessage());
        }


        return h;
    }

}
