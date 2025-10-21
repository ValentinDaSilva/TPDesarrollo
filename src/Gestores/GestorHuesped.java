package Gestores;

import ClasesDeDominio.Huesped;
import ClasesDTO.HuespedDTO;
import ClasesDAO.HuespedDAO;
import ClasesDAO.HuespedDAOImp;
import Excepciones.HuespedNoEncontradoException;

public class GestorHuesped {
    private HuespedDAO dao = new HuespedDAOImp();

    public HuespedDTO buscarHuesped(String tipoDoc, String nroDoc)
            throws HuespedNoEncontradoException {
        Huesped huesped = dao.getHuesped(tipoDoc, nroDoc);
        if (huesped == null) {
            throw new HuespedNoEncontradoException("No se encontró ningún huésped con ese documento.");
        }
        return new HuespedDTO(
                huesped.getNombre() + " " + huesped.getApellido(),
                huesped.getTipoDocumento(),
                huesped.getNumeroDocumento(),
                huesped.getTelefono(),
                huesped.getEmail(),
                huesped.getNacionalidad(),
                huesped.getDireccion()
        );
    }
}
