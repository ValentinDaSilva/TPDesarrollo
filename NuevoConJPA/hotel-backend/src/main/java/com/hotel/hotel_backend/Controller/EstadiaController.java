// [src/main/java/com/hotel/hotel_backend/Controller/EstadiaController.java]
package com.hotel.hotel_backend.Controller;

import com.hotel.hotel_backend.dto.EstadiaDTO;
import com.hotel.hotel_backend.service.EstadiaService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/estadia")
public class EstadiaController {

    private final EstadiaService estadiaService;

    public EstadiaController(EstadiaService estadiaService) {
        this.estadiaService = estadiaService;
    }

    // ============================================================
    //                        GENERAR ESTADÍA (CHECK-IN)
    // ============================================================
    @PostMapping("/checkin")
    public ResponseEntity<?> generarCheckIn(@RequestBody EstadiaDTO dto) {
        try {
            String respuesta = estadiaService.generarEstadia(dto);
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ============================================================
    //                        CHECK-OUT
    // ============================================================
    @PostMapping("/checkout/{id}")
    public ResponseEntity<?> registrarCheckOut(@PathVariable("id") Integer idEstadia) {
        try {
            EstadiaDTO respuesta = estadiaService.registrarCheckOut(idEstadia);
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ============================================================
    //           OBTENER ESTADÍA ACTIVA POR HABITACIÓN
    // ============================================================
    @GetMapping("/activa/{nroHabitacion}")
    public ResponseEntity<?> obtenerActiva(
            @PathVariable("nroHabitacion") int nroHabitacion) {

        try {
            EstadiaDTO dto = estadiaService.obtenerEstadiaActivaPorHabitacion(nroHabitacion);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
