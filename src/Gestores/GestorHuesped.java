package Gestores;
import ClasesDAO.HuespedDAO;
import ClasesDAO.HuespedDAOImp;
import ClasesDeDominio.Huesped;

public class GestorHuesped {
    private HuespedDAO dao;
    public GestorHuesped() {
        this.dao = new HuespedDAOImp();
    }
    public Huesped buscarHuesped(String tipoDocumento, String numeroDocumento) {
        Huesped huesped = dao.getHuesped(tipoDocumento, numeroDocumento);
        
        return huesped;
    }
}
