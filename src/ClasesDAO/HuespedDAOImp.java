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
            
        }  catch (FileNotFoundException e) {
            System.out.println("No se encontro el archivo de huespedes");
            return new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de huespedes");
            return new ArrayList<>();
        } catch (Exception e) {
            System.out.println("Ocurrio un error inesperado: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    @Override
    public Huesped getHuesped(String tipoDocumento, String numeroDocumento) {
        return obtenerTodos().stream().filter(e -> e.getTipoDocumento().equals(tipoDocumento) && e.getNumeroDocumento().equals(numeroDocumento)).findFirst().orElse(null);
    }

    @Override
    public void putHuesped(Huesped huesped){
        List<Huesped> huespedes = obtenerTodos();
        huespedes.add(huesped);
        try(Writer writer = new FileWriter("datos/huespedes.json")) {
            Gson gson = new Gson();
            gson.toJson(huespedes, writer);
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo de huespedes: " + e.getMessage());
            
        }
    }
    
    @Override
    public void updateHuesped(Huesped huesped){
        List<Huesped> huespedes = obtenerTodos();
        for(int i = 0; i < huespedes.size(); i++){
            Huesped actual = huespedes.get(i);
            if(actual.getTipoDocumento().equals(huesped.getTipoDocumento()) && actual.getNumeroDocumento().equals(huesped.getNumeroDocumento())){
                huespedes.set(i, huesped);
                escribirEnArchivo(huespedes);
                break;
            }   
        }
        return;
    }
    @Override    
    public void deleteHuesped(Huesped huesped){
        return;
    }
    @Override
    public void escribirEnArchivo(List<Huesped> huespedes){
        try (Writer write = new FileWriter("datos/huespedes.json")){
            Gson gson = new Gson();
            gson.toJson(huespedes, write);

        } catch (Exception e) {
            // 
        }
        return;
    }
}