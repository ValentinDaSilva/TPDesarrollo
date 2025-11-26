package com.hotel.hotel_backend.Controller;

import com.hotel.hotel_backend.dao.EstadiaDAOEnMemoria;
import com.hotel.hotel_backend.dao.ReservaDAOEnMemoria;
import com.hotel.hotel_backend.dao.HabitacionDAOEnMemoria;
import com.hotel.hotel_backend.dto.EstadiaDTO;
import com.hotel.hotel_backend.service.EstadiaService;
import com.hotel.hotel_backend.service.HabitacionService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/estadias")
public class EstadiaController {

    private final EstadiaService estadiaService;

    public EstadiaController() {

        HabitacionService habitacionService =
                new HabitacionService(HabitacionDAOEnMemoria.getInstance());

        this.estadiaService = new EstadiaService(
                EstadiaDAOEnMemoria.getInstance(),
                ReservaDAOEnMemoria.getInstance(),
                habitacionService
        );
    }

    // ============================================================
    //                GENERAR ESTADÍA (CHECK-IN)
    // ============================================================
    @PostMapping
    public ResponseEntity<?> generar(@RequestBody EstadiaDTO dto) {
        try {
            EstadiaDTO respuesta = estadiaService.generarEstadia(dto);
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ============================================================
    //      OBTENER ESTADÍA ACTIVA POR NÚMERO DE HABITACIÓN
    // ============================================================
    @GetMapping("/activa/{numeroHabitacion}")
    public ResponseEntity<?> obtenerActiva(@PathVariable int numeroHabitacion) {
        try {
            EstadiaDTO respuesta = estadiaService.obtenerEstadiaActivaPorHabitacion(numeroHabitacion);
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
