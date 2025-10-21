package ClasesDAO;
import java.util.List;

import ClasesDTO.HuespedDTO;


public interface HuespedDAO {
    List<HuespedDTO> obtenerTodos();
    HuespedDTO getHuesped(String tipoDocumento, String numeroDocumento);
    void putHuesped(HuespedDTO huesped);
    void updateHuesped(HuespedDTO huesped);
    void deleteHuesped(HuespedDTO huesped);
    void escribirEnArchivo(List<HuespedDTO> huespedes);
} 
