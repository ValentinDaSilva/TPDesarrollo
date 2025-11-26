package ClasesDAO;

import com.google.gson.Gson;
import ClasesDTO.HuespedDTO;
import java.io.*;
import java.util.*;

public class HuespedDAOImp implements HuespedDAO {

    private static HuespedDAOImp instancia;

    private List<HuespedDTO> huespedes;

    private static final String RUTA_ARCHIVO = "datos/huespedes.json";

    private HuespedDAOImp() {
        this.huespedes = cargarDesdeArchivo();
    }

    public static HuespedDAOImp getInstancia() {
        if (instancia == null) {
            instancia = new HuespedDAOImp();
        }
        return instancia;
    }


    @Override
    public List<HuespedDTO> obtenerTodos() {
        return new ArrayList<>(huespedes); 
    }

    @Override
    public HuespedDTO getHuesped(String tipoDocumento, String numeroDocumento) {
        return huespedes.stream()
                .filter(e -> e.getTipoDocumento().equalsIgnoreCase(tipoDocumento)
                        && e.getNumeroDocumento().equals(numeroDocumento))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void putHuesped(HuespedDTO huesped) {
        huespedes.add(huesped);
        escribirEnArchivo(); // actualiza el JSON
    }

    @Override
    public void updateHuesped(HuespedDTO huesped) {
        for (int i = 0; i < huespedes.size(); i++) {
            HuespedDTO actual = huespedes.get(i);
            if (actual.getTipoDocumento().equals(huesped.getTipoDocumento()) &&
                actual.getNumeroDocumento().equals(huesped.getNumeroDocumento())) {
                huespedes.set(i, huesped);
                escribirEnArchivo();
                return;
            }
        }
    }

    @Override
    public void deleteHuesped(HuespedDTO huesped) {
        huespedes.removeIf(actual -> 
            actual.getTipoDocumento().equals(huesped.getTipoDocumento()) &&
            actual.getNumeroDocumento().equals(huesped.getNumeroDocumento())
        );
        escribirEnArchivo();
    }


    private List<HuespedDTO> cargarDesdeArchivo() {
        try (Reader reader = new FileReader(RUTA_ARCHIVO)) {
            Gson gson = new Gson();
            HuespedDTO[] huespedesArray = gson.fromJson(reader, HuespedDTO[].class);
            return huespedesArray != null ? 
                    new ArrayList<>(Arrays.asList(huespedesArray)) : 
                    new ArrayList<>();
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el archivo de huéspedes, creando uno nuevo...");
            return new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de huéspedes");
            return new ArrayList<>();
        } catch (Exception e) {
            System.out.println("Ocurrió un error inesperado: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void escribirEnArchivo() {
        try (Writer writer = new FileWriter(RUTA_ARCHIVO)) {
            Gson gson = new Gson();
            gson.toJson(huespedes, writer);
        } catch (Exception e) {
            System.out.println("Error al escribir en el archivo de huéspedes: " + e.getMessage());
        }
    }
}

