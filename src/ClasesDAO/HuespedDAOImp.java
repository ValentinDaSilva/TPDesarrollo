package ClasesDAO;

import com.google.gson.Gson;
import ClasesDTO.HuespedDTO;
import java.io.*;
import java.util.*;


public class HuespedDAOImp implements HuespedDAO {
    @Override   
    public List<HuespedDTO> obtenerTodos() {
        try (Reader reader = new FileReader("datos/huespedes.json")) {
            Gson gson = new Gson();
            HuespedDTO[] huespedesArray = gson.fromJson(reader, HuespedDTO[].class);
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
    public HuespedDTO getHuesped(String tipoDocumento, String numeroDocumento) {
        return obtenerTodos().stream().filter(e -> e.getTipoDocumento().equalsIgnoreCase(tipoDocumento) && e.getNumeroDocumento().equals(numeroDocumento)).findFirst().orElse(null);
    }

    @Override
    public void putHuesped(HuespedDTO huesped){
        List<HuespedDTO> huespedes = obtenerTodos();
        huespedes.add(huesped);
        escribirEnArchivo(huespedes);
    }
    
    @Override
    public void updateHuesped(HuespedDTO huesped){
        List<HuespedDTO> huespedes = obtenerTodos();
        for(int i = 0; i < huespedes.size(); i++){
            HuespedDTO actual = huespedes.get(i);
            if(actual.getTipoDocumento().equals(huesped.getTipoDocumento()) && actual.getNumeroDocumento().equals(huesped.getNumeroDocumento())){
                huespedes.set(i, huesped);
                escribirEnArchivo(huespedes);
                break;
            }   
        }
        return;
    }
    @Override    
    public void deleteHuesped(HuespedDTO huesped){
        List<HuespedDTO> huespedes = obtenerTodos();
        huespedes.removeIf(actual -> actual.getTipoDocumento().equals(huesped.getTipoDocumento()) && actual.getNumeroDocumento().equals(huesped.getNumeroDocumento()));
        escribirEnArchivo(huespedes);
        return; 
    }
    @Override
    public void escribirEnArchivo(List<HuespedDTO> huespedes){
        try (Writer write = new FileWriter("datos/huespedes.json")){
            Gson gson = new Gson();
            gson.toJson(huespedes, write);

        } catch (Exception e) {
            System.out.println("Error al escribir en el archivo de huespedes: " + e.getMessage());
        }
        return;
    }
}