package com.hotel.hotel_backend.Controller;

import com.hotel.hotel_backend.domain.Habitacion;
import com.hotel.hotel_backend.service.HabitacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habitaciones")
public class HabitacionController {

    private final HabitacionService habitacionService;

    public HabitacionController(HabitacionService habitacionService) {
        this.habitacionService = habitacionService;
    }

    @GetMapping
    public ResponseEntity<?> listarTodas() {
        try {
            List<Habitacion> lista = habitacionService.obtenerTodas();
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{numero}")
    public ResponseEntity<?> obtenerUna(@PathVariable int numero) {
        try {
            Habitacion h = habitacionService.obtenerPorNumero(numero);
            return ResponseEntity.ok(h);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
