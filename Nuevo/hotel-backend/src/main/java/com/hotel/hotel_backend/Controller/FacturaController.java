package com.hotel.hotel_backend.Controller;

import com.hotel.hotel_backend.dao.FacturaDAOEnMemoria;
import com.hotel.hotel_backend.dto.FacturaDTO;
import com.hotel.hotel_backend.dto.NotaDeCreditoDTO;
import com.hotel.hotel_backend.service.FacturaService;

import java.time.LocalDate;

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

    @GetMapping("/pendientes/{numeroHabitacion}")
    public ResponseEntity<?> listarPendientesPorHabitacion(@PathVariable int numeroHabitacion) {
        try {
            return ResponseEntity.ok(facturaService.obtenerPendientesPorHabitacion(numeroHabitacion));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping
    public ResponseEntity<?> actualizar(@RequestBody FacturaDTO dto) {
        try {
            Object resultado = facturaService.actualizarFactura(dto);
            return ResponseEntity.ok(resultado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/notaDeCredito")
    public ResponseEntity<?> crearNota(@RequestBody NotaDeCreditoDTO dto) {
        try {
            return ResponseEntity.ok(facturaService.crearNotaDeCredito(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    
    @GetMapping("/pagos")
    public ResponseEntity<?> buscarPagosEntreFechas(
            @RequestParam String desde,
            @RequestParam String hasta
    ) {
        try {
            LocalDate fDesde = LocalDate.parse(desde);
            LocalDate fHasta = LocalDate.parse(hasta);

            return ResponseEntity.ok(
                    facturaService.buscarPagoesEntreFechas(fDesde, fHasta)
            );

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/cheques")
    public ResponseEntity<?> buscarChequesEntreFechas(
            @RequestParam String desde,
            @RequestParam String hasta
    ) {
        try {
            LocalDate fDesde = LocalDate.parse(desde);
            LocalDate fHasta = LocalDate.parse(hasta);

            return ResponseEntity.ok(
                    facturaService.buscarChequesEntreFechas(fDesde, fHasta)
            );

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
