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
                System.out.println("4. Dar de Baja Huésped");
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
                            System.out.println("Huésped eliminado correctamente.");
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
