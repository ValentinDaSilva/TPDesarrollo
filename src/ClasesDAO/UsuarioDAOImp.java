package ClasesDAO;

import com.google.gson.Gson;
import ClasesDTO.UsuarioDTO;
import java.io.*;
import java.util.*;

public class UsuarioDAOImp implements UsuarioDAO {

    private static UsuarioDAOImp instancia;

    private List<UsuarioDTO> usuarios;

    private static final String RUTA_ARCHIVO = "datos/usuarios.json";

    private UsuarioDAOImp() {
        this.usuarios = cargarDesdeArchivo();
    }

    public static UsuarioDAOImp getInstancia() {
        if (instancia == null) {
            instancia = new UsuarioDAOImp();
        }
        return instancia;
    }

    @Override
    public List<UsuarioDTO> obtenerTodos() {
        // Devuelve una copia para no modificar directamente la lista interna
        return new ArrayList<>(usuarios);
    }

    @Override
    public UsuarioDTO getUsuario(String nombreUsuario) {
        return usuarios.stream()
                .filter(e -> e.getNombreUsuario().equals(nombreUsuario))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void putUsuario(UsuarioDTO usuario) {
        usuarios.add(usuario);
        escribirEnArchivo();
    }

    @Override
    public void updateUsuario(UsuarioDTO usuario) {
        for (int i = 0; i < usuarios.size(); i++) {
            UsuarioDTO actual = usuarios.get(i);
            if (actual.getNombreUsuario().equals(usuario.getNombreUsuario())) {
                usuarios.set(i, usuario);
                escribirEnArchivo();
                return;
            }
        }
    }

    @Override
    public void deleteUsuario(UsuarioDTO usuario) {
        usuarios.removeIf(actual -> actual.getNombreUsuario().equals(usuario.getNombreUsuario()));
        escribirEnArchivo();
    }

    private List<UsuarioDTO> cargarDesdeArchivo() {
        try (Reader reader = new FileReader(RUTA_ARCHIVO)) {
            Gson gson = new Gson();
            UsuarioDTO[] usuariosArray = gson.fromJson(reader, UsuarioDTO[].class);
            return usuariosArray != null
                    ? new ArrayList<>(Arrays.asList(usuariosArray))
                    : new ArrayList<>();
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el archivo de usuarios, creando uno nuevo...");
            return new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de usuarios");
            return new ArrayList<>();
        } catch (Exception e) {
            System.out.println("Ocurrió un error inesperado: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void escribirEnArchivo() {
        try (Writer writer = new FileWriter(RUTA_ARCHIVO)) {
            Gson gson = new Gson();
            gson.toJson(usuarios, writer);
        } catch (Exception e) {
            System.out.println("Error al escribir en el archivo de usuarios: " + e.getMessage());
        }
    }


    @Override
    public boolean verificarPassword(UsuarioDTO usuario, String password) {
        return usuario.getPassword().equals(password);
    }

    @Override
    public boolean cambiarPassword(UsuarioDTO usuario, String passwordActual, String nuevaPassword) {
        if (verificarPassword(usuario, passwordActual)) {
            usuario.setPassword(nuevaPassword);
            updateUsuario(usuario); // 🔹 guarda el cambio y actualiza el JSON
            return true;
        }
        return false;
    }
}
