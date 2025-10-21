package ClasesDAO;

import com.google.gson.Gson;
import ClasesDTO.UsuarioDTO;
import java.io.*;
import java.util.*;


public class UsuarioDAOImp implements UsuarioDAO {
    @Override   
    public List<UsuarioDTO> obtenerTodos() {
        try (Reader reader = new FileReader("datos/usuarios.json")) {
            Gson gson = new Gson();
            UsuarioDTO[] usuariosArray = gson.fromJson(reader, UsuarioDTO[].class);
            return usuariosArray != null ? new ArrayList<>(Arrays.asList(usuariosArray)) : new ArrayList<>();
            
        }  catch (FileNotFoundException e) {
            System.out.println("No se encontro el archivo de usuarios");
            return new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de usuarios");
            return new ArrayList<>();
        } catch (Exception e) {
            System.out.println("Ocurrio un error inesperado: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    @Override
    public UsuarioDTO getUsuario(String nombreUsuario) {
        return obtenerTodos().stream().filter(e -> e.getNombreUsuario().equals(nombreUsuario)).findFirst().orElse(null);
    }

    @Override
    public void putUsuario(UsuarioDTO usuario){
        List<UsuarioDTO> usuarios = obtenerTodos();
        usuarios.add(usuario);
        try(Writer writer = new FileWriter("datos/usuarios.json")) {
            Gson gson = new Gson();
            gson.toJson(usuarios, writer);
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo de usuarios: " + e.getMessage());
            
        }
    }
    
    @Override
    public void updateUsuario(UsuarioDTO usuario){
        List<UsuarioDTO> usuarios = obtenerTodos();
        for(int i = 0; i < usuarios.size(); i++){
            UsuarioDTO actual = usuarios.get(i);
            if(actual.getNombreUsuario().equals(usuario.getNombreUsuario())){
                usuarios.set(i, usuario);
                escribirEnArchivo(usuarios);
                break;
            }   
        }
        return;
    }
    @Override    
    public void deleteUsuario(UsuarioDTO usuario){
        List<UsuarioDTO> usuarios = obtenerTodos();
        usuarios.removeIf(actual -> actual.getNombreUsuario().equals(usuario.getNombreUsuario()));
        escribirEnArchivo(usuarios);
        return; 
    }
    @Override
    public void escribirEnArchivo(List<UsuarioDTO> usuarios){
        try (Writer write = new FileWriter("datos/usuarios.json")){
            Gson gson = new Gson();
            gson.toJson(usuarios, write);
        } catch (Exception e) {
            System.out.println("Error al escribir en el archivo de usuarios: " + e.getMessage());
        }
        return;
    }
    
    @Override
    public boolean verificarPassword(UsuarioDTO usuario, String password) {
        return usuario.getPassword().equals(password);
    }
    @Override
    public boolean cambiarPassword(UsuarioDTO usuario, String passwordActual, String nuevaPassword) {
        if (verificarPassword(usuario, passwordActual)) {
            usuario.setPassword(nuevaPassword);
            return true;
        }
        return false;
    }
    
}