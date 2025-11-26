package com.hotel.hotel_backend.Controller;

import com.hotel.hotel_backend.dao.FacturaDAOEnMemoria;
import com.hotel.hotel_backend.dto.FacturaDTO;
import com.hotel.hotel_backend.service.FacturaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    private final FacturaService facturaService;

    public FacturaController() {
        this.facturaService = new FacturaService(FacturaDAOEnMemoria.getInstance());
    }

    @PostMapping
    public ResponseEntity<?> generar(@RequestBody FacturaDTO dto) {
        try {
            String msg = facturaService.generarFactura(dto);
            return ResponseEntity.ok(msg);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable int id) {
        try {
            return ResponseEntity.ok(facturaService.obtenerFacturaPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(facturaService.listarTodas());
    }
}
