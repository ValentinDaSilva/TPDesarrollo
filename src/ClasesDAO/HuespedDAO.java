package ClasesDAO;
import java.util.List;
import ClasesDeDominio.Huesped;


public interface HuespedDAO {
    List<Huesped> obtenerTodos();
    Huesped getHuesped(String tipoDocumento, String numeroDocumento);
    void putHuesped(Huesped huesped);
    void getHuesped(Huesped huesped);
    void updateHuesped(Huesped huesped);
    void deleteHuesped(Huesped huesped);
    void escribirEnArchivo(List<Huesped> huespedes);

} 
