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
                System.out.println("0. Salir");
                System.out.print("Seleccione una opción: ");
                opcion = sc.nextInt();
                sc.nextLine(); // limpia buffer

                switch (opcion) {
                    case 1:
                        System.out.println("\n--- BUSCAR HUÉSPED ---");
                        System.out.print("Tipo de documento (DNI/PAS): ");
                        String tipoDoc = sc.nextLine();
                        System.out.print("Número de documento: ");
                        String nroDoc = sc.nextLine();

                        try {
                            Huesped huesped = gestorHuesped.buscarHuesped(tipoDoc, nroDoc);
                            System.out.println("\nHuésped encontrado:");
                            System.out.println("Nombre completo: " + huesped.getNombre() + " " + huesped.getApellido());
                            System.out.println("Documento: " + huesped.getTipoDocumento() + " " + huesped.getNumeroDocumento());
                            System.out.println("Teléfono: " + huesped.getTelefono());
                            System.out.println("Email: " + huesped.getEmail());
                            System.out.println("Nacionalidad: " + huesped.getNacionalidad());
                            System.out.println("Dirección: " + huesped.getDireccionCompleta());
                        } catch (HuespedNoEncontradoException e) {
                            System.out.println("❌ " + e.getMessage());
                        }
                        break;

                    case 0:
                        System.out.println(" Saliendo del sistema...");
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
}
