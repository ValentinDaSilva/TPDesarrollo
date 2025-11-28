// [src/main/java/com/hotel/hotel_backend/Controller/ReservaController.java]
package com.hotel.hotel_backend.Controller;

import com.hotel.hotel_backend.dto.ReservaDTO;
import com.hotel.hotel_backend.service.ReservaService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    // CREAR RESERVA
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody ReservaDTO dto) {

        try {
            ReservaDTO respuesta = reservaService.reservarHabitacion(dto);
            return ResponseEntity.ok(respuesta);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // LISTAR ENTRE FECHAS
    @GetMapping("/entre")
    public ResponseEntity<?> listarEntre(@RequestParam String inicio, @RequestParam String fin) {
        try {
            List<ReservaDTO> lista = reservaService.listarReservasEntre(inicio, fin);
            return ResponseEntity.ok(lista);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // CANCELAR
    @PutMapping("/cancelar")
    public ResponseEntity<?> cancelar(@RequestBody ReservaDTO dto) {
        try {
            ReservaDTO respuesta = reservaService.cancelarReserva(dto);
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
