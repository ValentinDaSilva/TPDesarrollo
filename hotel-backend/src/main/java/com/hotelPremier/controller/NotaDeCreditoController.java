package com.hotelPremier.controller;

import com.hotelPremier.classes.DTO.DetalleNotaDeCreditoDTO;
import com.hotelPremier.classes.DTO.FacturaDTO;
import com.hotelPremier.classes.DTO.NotaDeCreditoDTO;
import com.hotelPremier.service.NotaDeCreditoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

@RestController
@RequestMapping("/notadecredito")
@Tag(name = "Notas de Crédito", description = "Gestión de notas de crédito")
public class NotaDeCreditoController {

    @Autowired
    private NotaDeCreditoService service;

    @Operation(
        summary = "Buscar facturas pendientes",
        description = "Busca facturas pendientes de pago por CUIT o por DNI y tipo de documento"
    )
    @GetMapping("/facturas-pendientes")
    public ResponseEntity<List<FacturaDTO>> buscarFacturasPendientes(
        @RequestParam(required = false) String cuit,
        @RequestParam(required = false) String dni,
        @RequestParam(required = false) String tipoDocumento
    ) {
        List<FacturaDTO> facturas = service.buscarFacturasPendientes(cuit, dni, tipoDocumento);
        return ResponseEntity.ok(facturas);
    }

    @Operation(
        summary = "Ingresar nota de crédito",
        description = "Genera una nota de crédito que anula contablemente una o más facturas"
    )
    @PostMapping
    public ResponseEntity<DetalleNotaDeCreditoDTO> ingresarNotaCredito(@RequestBody NotaDeCreditoDTO dto) {
        DetalleNotaDeCreditoDTO detalle = service.ingresarNotaDeCredito(dto);
        return ResponseEntity.ok(detalle);
    }
}
