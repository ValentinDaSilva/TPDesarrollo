// [src/main/java/com/hotel/hotel_backend/Controller/HuespedController.java]
package com.hotel.hotel_backend.Controller;

import com.hotel.hotel_backend.dto.HuespedDTO;
import com.hotel.hotel_backend.service.HuespedService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/huespedes")
public class HuespedController {

    private final HuespedService service;

    public HuespedController(HuespedService service) {
        this.service = service;
    }

    // CREAR
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody HuespedDTO dto) {
        try {
            return ResponseEntity.ok(service.crearHuesped(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    // MODIFICAR
    @PutMapping
    public ResponseEntity<?> modificar(@RequestBody HuespedDTO dto) {
        try {
            return ResponseEntity.ok(service.modificarHuesped(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // BUSCAR POR DOCUMENTO
    @GetMapping("/{documento}")
    public ResponseEntity<?> buscarPorDocumento(@PathVariable String documento) {
        try {
            return ResponseEntity.ok(service.buscarPorDocumento(documento));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // FILTROS
    @GetMapping("/buscar")
    public ResponseEntity<?> buscarConFiltros(
            @RequestParam(required = false) String apellido,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String tipoDocumento,
            @RequestParam(required = false) String numeroDocumento) {

        return ResponseEntity.ok(
                service.buscarConFiltros(apellido, nombre, tipoDocumento, numeroDocumento)
        );
    }

    // LISTAR TODOS
    @GetMapping
    public ResponseEntity<?> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    // ELIMINAR
    @DeleteMapping
    public ResponseEntity<?> eliminar(@RequestBody HuespedDTO dto) {
        try {
            service.eliminarHuesped(dto);
            return ResponseEntity.ok("Huésped eliminado correctamente.");
        } catch (RuntimeException e) {
            if (e.getMessage().equals("CONFIRMAR_ELIMINACION")) {
                return ResponseEntity.status(409).body("CONFIRMAR_ELIMINACION");
            }
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
