package com.hotelPremier.controller;

import com.hotelPremier.service.ResponsablePagoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/responsablesPago")
@Tag(name = "Responsables de Pago", description = "Búsqueda de responsables de pago")
public class ResponsablePagoController {

    @Autowired
    private ResponsablePagoService responsablePagoService;

    @GetMapping
    public ResponseEntity<ResponsablePagoIdResponse> buscarResponsablePago(
        @RequestParam(required = false) String dni,
        @RequestParam(required = false) String tipoDocumento,
        @RequestParam(required = false) String cuit
    ) {
        var result = responsablePagoService.buscarResponsablePago(dni, tipoDocumento, cuit);
        return ResponseEntity.ok(
            new ResponsablePagoIdResponse(result.id(), result.razonSocial())
        );
    }

    /**
     * DTO de respuesta simple para el frontend
     */
    private record ResponsablePagoIdResponse(
        Integer id,
        String razonSocial
    ) {}
}
