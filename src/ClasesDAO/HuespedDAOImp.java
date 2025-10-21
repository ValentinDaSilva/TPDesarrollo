package ClasesDAO;

import ClasesDeDominio.Huesped;
import com.google.gson.Gson;
import java.io.*;
import java.util.*;


public class HuespedDAOImp implements HuespedDAO {
    @Override   
    public List<Huesped> obtenerTodos() {
        try (Reader reader = new FileReader("datos/huespedes.json")) {
            Gson gson = new Gson();
            Huesped[] huespedesArray = gson.fromJson(reader, Huesped[].class);
            return huespedesArray != null ? new ArrayList<>(Arrays.asList(huespedesArray)) : new ArrayList<>();
            
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    @Override
    public Huesped buscarPorDocumento(String tipoDocumento, String numeroDocumento) {
        // lo implementarás más adelante
        return null;
    }

    @Override
    public void putHuesped(Huesped huesped){
        return ;
    }
    @Override
    public void getHuesped(Huesped huesped){
        return;
    }
    @Override
    public void updateHuesped(Huesped huesped){
        return;
    }
    @Override    
    public void deleteHuesped(Huesped huesped){
        return;
    }
}