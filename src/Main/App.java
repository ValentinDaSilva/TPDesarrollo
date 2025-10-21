package Main;

import java.util.Scanner;
import ClasesDeDominio.Usuario;
import Gestores.GestorUsuarios;
import Excepciones.UsuarioNoExistenteException;
import Excepciones.ContrasenaIncorrectaException;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        GestorUsuarios gestor = new GestorUsuarios();

        System.out.println("=== LOGIN DEL SISTEMA ===");
        System.out.print("Usuario: ");
        String nombre = sc.nextLine();
        System.out.print("Contraseña: ");
        String pass = sc.nextLine();

        try {
            Usuario usuario = gestor.autenticar(nombre, pass);
            System.out.println("Bienvenido " + usuario.getNombreUsuario());
        } catch (UsuarioNoExistenteException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (ContrasenaIncorrectaException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
}
