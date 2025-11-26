//ProyectoConsolidado.java
{

}

//src/ClasesDAO/DAOFactory.java
{
package ClasesDAO;

public class DAOFactory {

    private static DAOFactory instancia;

    private DAOFactory() {}

    public static DAOFactory getInstancia() {
        if (instancia == null) {
            instancia = new DAOFactory();
        }
        return instancia;
    }

    public HuespedDAO getHuespedDAO() {
        return HuespedDAOImp.getInstancia();
    }

    public UsuarioDAO getUsuarioDAO() {
        return UsuarioDAOImp.getInstancia();
    }

}

}

//src/ClasesDAO/HuespedDAO.java
{
package ClasesDAO;
import java.util.List;

import ClasesDTO.HuespedDTO;


public interface HuespedDAO {
    List<HuespedDTO> obtenerTodos();
    HuespedDTO getHuesped(String tipoDocumento, String numeroDocumento);
    void putHuesped(HuespedDTO huesped);
    void updateHuesped(HuespedDTO huesped);
    void deleteHuesped(HuespedDTO huesped);
} 

}

//src/ClasesDAO/HuespedDAOImp.java
{
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


}

//src/ClasesDAO/UsuarioDAO.java
{
package ClasesDAO;
import java.util.List;

import ClasesDTO.UsuarioDTO;


public interface UsuarioDAO {
    List<UsuarioDTO> obtenerTodos();
    UsuarioDTO getUsuario(String nombreUsuario);
    void putUsuario(UsuarioDTO usuario);
    void updateUsuario(UsuarioDTO usuario);
    void deleteUsuario(UsuarioDTO usuario);
    boolean verificarPassword(UsuarioDTO usuario,String password);
    boolean cambiarPassword(UsuarioDTO usuario, String passwordActual, String nuevaPassword);
} 

}

//src/ClasesDAO/UsuarioDAOImp.java
{
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

}

//src/ClasesDeDominio/Direccion.java
{
package ClasesDeDominio;

public class Direccion {
    private String calle;
    private String numero;
    private String departamento;
    private String piso;
    private String codigoPostal;
    private String localidad;
    private String provincia;
    private String pais;

    public Direccion() {}

    public Direccion(String calle, String numero, String departamento, String piso,
                     String codigoPostal, String localidad, String provincia, String pais) {
        this.calle = calle;
        this.numero = numero;
        this.departamento = departamento;
        this.piso = piso;
        this.codigoPostal = codigoPostal;
        this.localidad = localidad;
        this.provincia = provincia;
        this.pais = pais;
    }

    // Getters y setters
    public String getCalle() { return calle; }
    public void setCalle(String calle) { this.calle = calle; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    public String getPiso() { return piso; }
    public void setPiso(String piso) { this.piso = piso; }

    public String getCodigoPostal() { return codigoPostal; }
    public void setCodigoPostal(String codigoPostal) { this.codigoPostal = codigoPostal; }

    public String getLocalidad() { return localidad; }
    public void setLocalidad(String localidad) { this.localidad = localidad; }

    public String getProvincia() { return provincia; }
    public void setProvincia(String provincia) { this.provincia = provincia; }

    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }

    @Override
    public String toString() {
        return calle + " " + numero + ", " +
               (departamento != null ? "Dpto. " + departamento + ", " : "") +
               (piso != null ? "Piso " + piso + ", " : "") +
               localidad + " (" + codigoPostal + "), " + provincia + ", " + pais;
    }
}

}

//src/ClasesDeDominio/Huesped.java
{
package ClasesDeDominio;

public class Huesped {

    private String apellido;
    private String nombre;
    private String tipoDocumento;
    private String numeroDocumento;
    private String cuit;
    private String posicionIVA;
    private Direccion direccion;  // Composición
    private String telefono;
    private String email;
    private String ocupacion;
    private String nacionalidad;
    private String direccionCompleta;
    public Huesped() {
    }

    public Huesped(String apellido, String nombre, String tipoDocumento, String numeroDocumento,
                   String cuit, String posicionIVA, Direccion direccion,
                   String telefono, String email, String ocupacion, String nacionalidad) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.cuit = cuit;
        this.posicionIVA = posicionIVA;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.ocupacion = ocupacion;
        this.nacionalidad = nacionalidad;
        this.direccionCompleta = direccion != null
                ? direccion.getCalle() + " " + direccion.getNumero() + ", " +
                  direccion.getLocalidad() + ", " + direccion.getProvincia()
                : "";
    }

    // ======== Getters y Setters ========

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getPosicionIVA() {
        return posicionIVA;
    }

    public void setPosicionIVA(String posicionIVA) {
        this.posicionIVA = posicionIVA;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }
    public String getDireccionCompleta() {
        return direccionCompleta;
    }
    public void setDireccionCompleta(Direccion direccion) {
        this.direccionCompleta = direccion != null
                ? direccion.getCalle() + " " + direccion.getNumero() + ", " +
                  direccion.getLocalidad() + ", " + direccion.getProvincia()
                : "";
    }


    // Validadores para cada campo (sin expresiones regulares)
    public boolean validarApellido() {
        if (apellido == null) return false;
        return todosLetrasOEspacios(apellido);
    }

    public boolean validarNombre() {
        if (nombre == null) return false;
        return todosLetrasOEspacios(nombre);
    }

    public boolean validarTipoDocumento() {
        if (tipoDocumento == null) return false;
        String t = tipoDocumento.trim().toUpperCase();
        java.util.Set<String> aceptados = new java.util.HashSet<>(java.util.Arrays.asList(
            "DNI", "PASAPORTE", "LC", "LE", "CI"
        ));
        return aceptados.contains(t);
    }

    public boolean validarNumeroDocumento() {
        if (numeroDocumento == null) return false;
        return todosNumeros(numeroDocumento);
    }

    public boolean validarCuit() {
        if (cuit == null) return false;
        for (int i = 0; i < cuit.length(); i++) {
            char ch = cuit.charAt(i);
            if (!Character.isDigit(ch)) {
                return false;
            }
        }
        return true;
    }

    public boolean validarPosicionIVA() {
        if (posicionIVA == null) return false;
        String p = posicionIVA.trim().toUpperCase();
        java.util.Set<String> aceptadas = new java.util.HashSet<>(java.util.Arrays.asList(
            "RESPONSABLE INSCRIPTO", "MONOTRIBUTISTA", "CONSUMIDOR FINAL", "EXENTO", "NO RESPONSABLE"
        ));
        return aceptadas.contains(p);
    }

    public boolean validarDireccion() {
        return todosLetrasOEspacios(direccion.getCalle()) &&
               todosNumeros(String.valueOf(direccion.getNumero())) &&
               todosLetrasOEspacios(direccion.getLocalidad()) &&
               todosLetrasOEspacios(direccion.getProvincia());
    }

    public boolean validarTelefono() {
        if (telefono == null) return false;
        return todosNumeros(String.valueOf(telefono));
    }

    public boolean validarEmail() {
        if (email == null) return false;
        boolean tieneArroba = false;
        boolean tienePunto = false;
        for(int i = 0; i < email.length(); i++) {
            char c = email.charAt(i);
            if (c == ' ' || c == ',') {
                return false;
            }
            if(c == '@') {
                tieneArroba = true;
            }
            if(c == '.') {
                tienePunto = true;
            }
        }

        return (tieneArroba && tienePunto);
    }

    public boolean validarOcupacion(String ocupacion) {
        if (ocupacion == null) return false;
        return todosLetrasOEspacios(ocupacion);
    }

    public boolean validarNacionalidad() {
        if (nacionalidad == null) return false;
        return todosLetrasOEspacios(nacionalidad);
    }
        
    private boolean todosNumeros(String s) {
        if (s == null || s.isEmpty()) return false;
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) return false;
        }
        return true;
    }

    private boolean todosLetrasOEspacios(String s) {
        if (s == null) return false;
        String t = s.trim();
        if (t.isEmpty()) return false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!(Character.isLetter(c) || c == ' ')) return false;
        }
        return true;
    }
    public boolean validar() {
        if (!validarApellido()) return false;
        if (!validarNombre()) return false;
        if (!validarTipoDocumento()) return false;
        if (!validarNumeroDocumento()) return false;
        if (!validarCuit()) return false;
        if (!validarPosicionIVA()) return false;
        if (direccion == null) return false;
        if (!validarDireccion()) return false;
        if (!validarTelefono()) return false;
        if (!validarEmail()) return false;
        if (!validarOcupacion(this.ocupacion)) return false;
        if (!validarNacionalidad()) return false;
        return true;
    }
}

}

//src/ClasesDeDominio/Usuario.java
{
package ClasesDeDominio;

public class Usuario {
    private String nombreUsuario;

    // Constructor completo
    public Usuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    // ===== Getters =====
    public String getNombreUsuario() {
        return nombreUsuario;
    }


    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

     public boolean igualNombreUsuario(String nombreUsuario) {
        return this.nombreUsuario.equals(nombreUsuario);
     }
}

}

//src/ClasesDTO/HuespedDTO.java
{
package ClasesDTO;

import ClasesDeDominio.Direccion;

public class HuespedDTO {
    private String nombre;
    private String apellido;
    private String tipoDocumento;
    private String numeroDocumento;
    private String telefono;
    private String email;
    private String nacionalidad;
    private String direccionCompleta;
    private Direccion direccion;
    private String posicionIVA;
    private String cuit;
    private String ocupacion;

    public HuespedDTO() {
    }

    public HuespedDTO(String apellido, String nombre, String tipoDocumento, String numeroDocumento,
                   String cuit, String posicionIVA, Direccion direccion,
                   String telefono, String email, String ocupacion, String nacionalidad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.telefono = telefono;
        this.email = email;
        this.nacionalidad = nacionalidad;
        this.direccion = direccion;
        this.cuit = cuit;
        this.posicionIVA = posicionIVA;
        this.ocupacion = ocupacion;
        this.direccionCompleta = direccion != null
                ? direccion.getCalle() + " " + direccion.getNumero() + ", " +
                  direccion.getLocalidad() + ", " + direccion.getProvincia()
                : "";
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public String getDireccionCompleta() {
        return direccionCompleta;
    }
    public Direccion getDireccion() {
        return direccion;
    }

    public String getPosicionIVA() {
        return posicionIVA;
    }
    public String getCuit() {
        return cuit;
    }
    public String getOcupacion() {
        return ocupacion;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public void setDireccionCompleta(String direccionCompleta) {
        this.direccionCompleta = direccionCompleta;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
        this.direccionCompleta = direccion != null
                ? direccion.getCalle() + " " + direccion.getNumero() + ", " +
                  direccion.getLocalidad() + ", " + direccion.getProvincia()
                : "";
    }

    public void setPosicionIVA(String posicionIVA) {
        this.posicionIVA = posicionIVA;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }
}

}

//src/ClasesDTO/UsuarioDTO.java
{
package ClasesDTO;
    
public class UsuarioDTO {
    private String nombreUsuario;
    private String password;

    public UsuarioDTO(String nombreUsuario, String password) {
        this.nombreUsuario = nombreUsuario;
        this.password = password;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

}

//src/Excepciones/ContrasenaIncorrectaException.java
{
package Excepciones;

public class ContrasenaIncorrectaException extends Exception {
    public ContrasenaIncorrectaException(String mensaje) {
        super(mensaje);
    }
}

}

//src/Excepciones/HuespedInvalidoException.java
{
package Excepciones;

public class HuespedInvalidoException extends Exception {
    public HuespedInvalidoException(String mensaje) {
        super(mensaje);
    }
}

}

//src/Excepciones/HuespedNoEncontradoException.java
{
package Excepciones;

public class HuespedNoEncontradoException extends Exception {
    public HuespedNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}

}

//src/Excepciones/HuespedYaExistenteException.java
{
package Excepciones;

public class HuespedYaExistenteException extends Exception {
    public HuespedYaExistenteException(String mensaje) {
        super(mensaje);
    }
}

}

//src/Excepciones/UsuarioNoExistenteException.java
{
package Excepciones;

public class UsuarioNoExistenteException extends Exception {
    public UsuarioNoExistenteException(String mensaje) {
        super(mensaje);
    }
}

}

//src/Gestores/GestorHuesped.java
{
package Gestores;

import ClasesDeDominio.Huesped;
import ClasesDTO.HuespedDTO;
import ClasesDAO.DAOFactory;
import ClasesDAO.HuespedDAO;
import Excepciones.HuespedYaExistenteException;
import Excepciones.HuespedInvalidoException;
import Excepciones.HuespedNoEncontradoException;

/**
 * Gestor encargado de manejar las operaciones de negocio
 * relacionadas con los huéspedes.
 *
 * Interactúa con el HuespedDAO (obtenido a través de DAOFactory)
 * para realizar las operaciones de búsqueda, alta, modificación
 * y eliminación sobre los huéspedes del sistema.
 */
public class GestorHuesped {

    private final HuespedDAO dao;

    public GestorHuesped() {
        this.dao = DAOFactory.getInstancia().getHuespedDAO();
    }

    public Huesped buscarHuesped(String tipoDoc, String nroDoc)
            throws HuespedNoEncontradoException {
        HuespedDTO dto = dao.getHuesped(tipoDoc, nroDoc);
        if (dto == null) {
            throw new HuespedNoEncontradoException(
                "No se encontró ningún huésped con ese documento."
            );
        }
        return convertirAClaseDominio(dto);
    }

    public void registrarHuesped(Huesped huesped)
            throws HuespedYaExistenteException, HuespedInvalidoException {

        HuespedDTO dtoExistente = dao.getHuesped(
            huesped.getTipoDocumento(),
            huesped.getNumeroDocumento()
        );

        if (dtoExistente != null) {
            throw new HuespedYaExistenteException(
                "Ya existe un huésped con ese tipo y número de documento."
            );
        }

        if (huesped.getNombre() == null || huesped.getApellido() == null) {
            throw new HuespedInvalidoException("El huésped debe tener nombre y apellido.");
        }

        dao.putHuesped(convertirADTO(huesped));
    }

    public void modificarHuesped(Huesped huesped) {
        dao.updateHuesped(convertirADTO(huesped));
    }

    public void eliminarHuesped(Huesped huesped) {
        dao.deleteHuesped(convertirADTO(huesped));
    }

    private HuespedDTO convertirADTO(Huesped huesped) {
        HuespedDTO dto = new HuespedDTO();
        dto.setApellido(huesped.getApellido());
        dto.setNombre(huesped.getNombre());
        dto.setTipoDocumento(huesped.getTipoDocumento());
        dto.setNumeroDocumento(huesped.getNumeroDocumento());
        dto.setCuit(huesped.getCuit());
        dto.setPosicionIVA(huesped.getPosicionIVA());
        dto.setDireccion(huesped.getDireccion());
        dto.setTelefono(huesped.getTelefono());
        dto.setEmail(huesped.getEmail());
        dto.setOcupacion(huesped.getOcupacion());
        dto.setNacionalidad(huesped.getNacionalidad());
        return dto;
    }

    private Huesped convertirAClaseDominio(HuespedDTO dto) {
        Huesped h = new Huesped();
        h.setApellido(dto.getApellido());
        h.setNombre(dto.getNombre());
        h.setTipoDocumento(dto.getTipoDocumento());
        h.setNumeroDocumento(dto.getNumeroDocumento());
        h.setCuit(dto.getCuit());
        h.setPosicionIVA(dto.getPosicionIVA());
        h.setDireccion(dto.getDireccion());
        h.setTelefono(dto.getTelefono());
        h.setEmail(dto.getEmail());
        h.setOcupacion(dto.getOcupacion());
        h.setNacionalidad(dto.getNacionalidad());
        h.setDireccionCompleta(dto.getDireccion());
        return h;
    }
}

}

//src/Gestores/GestorUsuarios.java
{
package Gestores;

import ClasesDAO.DAOFactory;
import ClasesDAO.UsuarioDAO;
import ClasesDeDominio.Usuario;
import ClasesDTO.UsuarioDTO;
import Excepciones.UsuarioNoExistenteException;
import Excepciones.ContrasenaIncorrectaException;

public class GestorUsuarios {
    
    private final UsuarioDAO dao;

    public GestorUsuarios() {
        this.dao = DAOFactory.getInstancia().getUsuarioDAO();
    }

    public Usuario autenticar(String nombreUsuario, String contrasena)
            throws UsuarioNoExistenteException, ContrasenaIncorrectaException {

        UsuarioDTO usuario = dao.getUsuario(nombreUsuario);

        if (usuario == null) {
            throw new UsuarioNoExistenteException(
                "El usuario '" + nombreUsuario + "' no existe."
            );
        }

        if (!usuario.getPassword().equals(contrasena)) {
            throw new ContrasenaIncorrectaException(
                "Contraseña incorrecta para el usuario '" + nombreUsuario + "'."
            );
        }

        // 🔹 Se crea el objeto de dominio
        Usuario usuarioDominio = new Usuario(nombreUsuario);

        return usuarioDominio;
    }
}

}

//src/Main/App.java
{
package Main;

import java.util.Scanner;

import Gestores.GestorUsuarios;
import Gestores.GestorHuesped;
import Excepciones.UsuarioNoExistenteException;
import Excepciones.ContrasenaIncorrectaException;
import Excepciones.HuespedNoEncontradoException;
import ClasesDeDominio.*;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        GestorUsuarios gestorUsuarios = new GestorUsuarios();
        GestorHuesped gestorHuesped = new GestorHuesped();

        System.out.println("=== SISTEMA DE GESTIÓN HOTELERA ===");

        try {
            // ====== LOGIN ======
            System.out.println("\n--- LOGIN ---");
            System.out.print("Usuario: ");
            String nombre = sc.nextLine();
            System.out.print("Contraseña: ");
            String pass = sc.nextLine();

            Usuario usuario = gestorUsuarios.autenticar(nombre, pass);
            System.out.println("Bienvenido " + usuario.getNombreUsuario());

            // ====== MENÚ PRINCIPAL ======
            int opcion = -1;
            while (opcion != 0) {
                System.out.println("\n--- MENÚ PRINCIPAL ---");
                System.out.println("1. Buscar Huésped");
                System.out.println("2. Dar de Alta Huésped");
                System.out.println("3. Modificar Huésped");
                System.out.println("3. Dar de Baja Huésped");
                System.out.println("0. Salir");
                System.out.print("Seleccione una opción: ");
                opcion = sc.nextInt();
                sc.nextLine(); 

                switch (opcion) {
                    case 1:
                        System.out.println("Buscar huésped existente.");
                        buscarHuesped(gestorHuesped);
                        break;

                    case 2:
                        System.out.println("Registrar nuevo huésped.");
                        Huesped nuevoHuesped = new Huesped();

                        System.out.print("Nombre: ");
                        nuevoHuesped.setNombre(sc.nextLine());
                        System.out.print("Apellido: ");
                        nuevoHuesped.setApellido(sc.nextLine());
                        System.out.print("Tipo de documento (DNI/PAS): ");
                        nuevoHuesped.setTipoDocumento(sc.nextLine());
                        System.out.print("Número de documento: ");
                        nuevoHuesped.setNumeroDocumento(sc.nextLine());
                        System.out.print("Teléfono: ");
                        nuevoHuesped.setTelefono(sc.nextLine());
                        System.out.print("Email: ");
                        nuevoHuesped.setEmail(sc.nextLine());
                        System.out.print("Nacionalidad: ");
                        nuevoHuesped.setNacionalidad(sc.nextLine());

                        System.out.println("--- Dirección ---");
                        System.out.print("Calle: ");
                        String calle = sc.nextLine();
                        System.out.print("Número: ");
                        String numero = sc.nextLine();
                        System.out.print("Piso (opcional): ");
                        String piso = sc.nextLine();
                        System.out.print("Departamento (opcional): ");
                        String departamento = sc.nextLine();
                        System.out.print("Ciudad: ");
                        String ciudad = sc.nextLine();
                        System.out.print("Provincia: ");
                        String provincia = sc.nextLine();
                        System.out.print("Código postal: ");
                        String codigoPostal = sc.nextLine();

                        Direccion direccion = new Direccion(calle, numero, departamento, piso, codigoPostal, ciudad, provincia, codigoPostal);
                        nuevoHuesped.setDireccion(direccion);

                        gestorHuesped.registrarHuesped(nuevoHuesped);
                        System.out.println("Huésped registrado exitosamente.");
                        break;

                    case 3:
                        System.out.println("Modificar datos de huésped.");
                        Huesped huesped = buscarHuesped(gestorHuesped);
                        if (huesped != null) {
                            modificarHuesped(huesped, gestorHuesped);
                        }
                        break;
                    case 4:
                        System.out.println("Eliminar huésped.");
                        Huesped huespedAEliminar = buscarHuesped(gestorHuesped);
                        if (huespedAEliminar != null) {
                            gestorHuesped.eliminarHuesped(huespedAEliminar);
                            System.out.println("Funcionalidad de eliminación no implementada aún.");
                        }
                        break;
                    case 0:
                        System.out.println("Saliendo del sistema...");
                        break;

                    default:
                        System.out.println("Opción no válida.");
                }
            }

        } catch (UsuarioNoExistenteException | ContrasenaIncorrectaException e) {
            System.out.println("Error de login: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }

        sc.close();
    }

    private static Huesped buscarHuesped(GestorHuesped gestorHuesped) {
        @SuppressWarnings("resource")
        Scanner sc = new Scanner(System.in);

        System.out.print("Tipo de documento (DNI/PAS): ");
        String tipoDoc = sc.nextLine();
        System.out.print("Número de documento: ");
        String numDoc = sc.nextLine();

        try {
            Huesped huesped = gestorHuesped.buscarHuesped(tipoDoc, numDoc);
            System.out.println("Huésped encontrado: " + huesped.getNombre() + " " + huesped.getApellido());
            return huesped;
        } catch (HuespedNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    private static void modificarHuesped(Huesped huesped, GestorHuesped gestorHuesped) {
        @SuppressWarnings("resource")
        Scanner sc = new Scanner(System.in);

        System.out.println("Presiona Enter para mantener el valor actual.");

        System.out.print("Nombre [" + huesped.getNombre() + "]: ");
        String entrada = sc.nextLine();
        if (!entrada.trim().isEmpty()) huesped.setNombre(entrada);

        System.out.print("Apellido [" + huesped.getApellido() + "]: ");
        entrada = sc.nextLine();
        if (!entrada.trim().isEmpty()) huesped.setApellido(entrada);

        System.out.print("Tipo de documento [" + huesped.getTipoDocumento() + "]: ");
        entrada = sc.nextLine();
        if (!entrada.trim().isEmpty()) huesped.setTipoDocumento(entrada);

        System.out.print("Número de documento [" + huesped.getNumeroDocumento() + "]: ");
        entrada = sc.nextLine();
        if (!entrada.trim().isEmpty()) huesped.setNumeroDocumento(entrada);

        System.out.print("Teléfono [" + huesped.getTelefono() + "]: ");
        entrada = sc.nextLine();
        if (!entrada.trim().isEmpty()) huesped.setTelefono(entrada);

        System.out.print("Email [" + huesped.getEmail() + "]: ");
        entrada = sc.nextLine();
        if (!entrada.trim().isEmpty()) huesped.setEmail(entrada);

        System.out.print("Nacionalidad [" + huesped.getNacionalidad() + "]: ");
        entrada = sc.nextLine();
        if (!entrada.trim().isEmpty()) huesped.setNacionalidad(entrada);

        Direccion dir = huesped.getDireccion();
        if (dir == null) {
            dir = new Direccion("", "", "", "", "", "", "", "");
        }

        System.out.println("--- Dirección ---");
        System.out.print("Calle [" + dir.getCalle() + "]: ");
        entrada = sc.nextLine();
        if (!entrada.trim().isEmpty()) dir.setCalle(entrada);

        System.out.print("Número [" + dir.getNumero() + "]: ");
        entrada = sc.nextLine();
        if (!entrada.trim().isEmpty()) dir.setNumero(entrada);

        System.out.print("Piso [" + dir.getPiso() + "]: ");
        entrada = sc.nextLine();
        if (!entrada.trim().isEmpty()) dir.setPiso(entrada);

        System.out.print("Departamento [" + dir.getDepartamento() + "]: ");
        entrada = sc.nextLine();
        if (!entrada.trim().isEmpty()) dir.setDepartamento(entrada);

        System.out.print("Ciudad [" + dir.getLocalidad() + "]: ");
        entrada = sc.nextLine();
        if (!entrada.trim().isEmpty()) dir.setLocalidad(entrada);

        System.out.print("Provincia [" + dir.getProvincia() + "]: ");
        entrada = sc.nextLine();
        if (!entrada.trim().isEmpty()) dir.setProvincia(entrada);

        System.out.print("Código postal [" + dir.getCodigoPostal() + "]: ");
        entrada = sc.nextLine();
        if (!entrada.trim().isEmpty()) dir.setCodigoPostal(entrada);

        // Asignar la dirección actualizada al huésped
        huesped.setDireccion(dir);

        try {
            gestorHuesped.modificarHuesped(huesped);
            System.out.println("Huésped actualizado correctamente.");
        } catch (Exception e) {
            System.out.println("Error al actualizar huésped: " + e.getMessage());
        }
    }
    
}

}

