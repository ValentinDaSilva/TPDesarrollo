package ClasesDAO;

import ClasesDeDominio.Usuario;
import com.google.gson.Gson;
import java.io.*;
import java.util.*;


public class UsuarioDAOImp implements UsuarioDAO {
    @Override   
    public List<Usuario> obtenerTodos() {
        try (Reader reader = new FileReader("datos/usuarios.json")) {
            Gson gson = new Gson();
            Usuario[] usuariosArray = gson.fromJson(reader, Usuario[].class);
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
    public Usuario getUsuario(String nombreUsuario) {
        return obtenerTodos().stream().filter(e -> e.getNombreUsuario().equals(nombreUsuario)).findFirst().orElse(null);
    }

    @Override
    public void putUsuario(Usuario usuario){
        List<Usuario> usuarios = obtenerTodos();
        usuarios.add(usuario);
        try(Writer writer = new FileWriter("datos/usuarios.json")) {
            Gson gson = new Gson();
            gson.toJson(usuarios, writer);
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo de usuarios: " + e.getMessage());
            
        }
    }
    
    @Override
    public void updateUsuario(Usuario usuario){
        List<Usuario> usuarios = obtenerTodos();
        for(int i = 0; i < usuarios.size(); i++){
            Usuario actual = usuarios.get(i);
            if(actual.getNombreUsuario().equals(usuario.getNombreUsuario())){
                usuarios.set(i, usuario);
                escribirEnArchivo(usuarios);
                break;
            }   
        }
        return;
    }
    @Override    
    public void deleteUsuario(Usuario usuario){
        List<Usuario> usuarios = obtenerTodos();
        usuarios.removeIf(actual -> actual.getNombreUsuario().equals(usuario.getNombreUsuario()));
        escribirEnArchivo(usuarios);
        return; 
    }
    @Override
    public void escribirEnArchivo(List<Usuario> usuarios){
        try (Writer write = new FileWriter("datos/usuarios.json")){
            Gson gson = new Gson();
            gson.toJson(usuarios, write);
        } catch (Exception e) {
            System.out.println("Error al escribir en el archivo de usuarios: " + e.getMessage());
        }
        return;
    }
}