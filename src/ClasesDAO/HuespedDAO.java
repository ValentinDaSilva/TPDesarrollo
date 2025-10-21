package ClasesDAO;
import java.util.List;
import ClasesDeDominio.Huesped;


public interface HuespedDAO {
    List<Huesped> obtenerTodos();
    Huesped buscarPorDocumento(String tipoDocumento, String numeroDocumento);
    void putHuesped(Huesped huesped);
    void getHuesped(Huesped huesped);
    void updateHuesped(Huesped huesped);
    void deleteHuesped(Huesped huesped);
} 
