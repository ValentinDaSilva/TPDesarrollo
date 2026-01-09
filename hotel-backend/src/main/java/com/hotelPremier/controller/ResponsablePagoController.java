package com.hotelPremier.controller;

import com.hotelPremier.classes.DTO.ResponsablePagoDTO;
import com.hotelPremier.service.ResponsablePagoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/responsablesPago")
@Tag(name = "Responsables de Pago", description = "Gestión de responsables de pago")
public class ResponsablePagoController {

    @Autowired
    private ResponsablePagoService responsablePagoService;

    @GetMapping
    public ResponseEntity<ResponsablePagoDTO> buscarResponsablePago(
        @RequestParam(required = false) String dni,
        @RequestParam(required = false) String tipoDocumento,
        @RequestParam(required = false) String cuit
    ) {
        ResponsablePagoDTO result = responsablePagoService.buscarResponsablePago(dni, tipoDocumento, cuit);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<ResponsablePagoDTO> altaResponsablePago(
        @RequestBody ResponsablePagoDTO dto
    ) {
        ResponsablePagoDTO result = responsablePagoService.altaResponsablePago(dto);
        return ResponseEntity.ok(result);
    }

    @PutMapping
    public ResponseEntity<ResponsablePagoDTO> modificarResponsablePago(
        @RequestBody ResponsablePagoDTO dto
    ) {
        ResponsablePagoDTO result = responsablePagoService.modificarResponsablePago(dto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping
    public ResponseEntity<Void> eliminarResponsablePago(
        @RequestBody ResponsablePagoDTO dto
    ) {
        responsablePagoService.eliminarResponsablePago(dto);
        return ResponseEntity.noContent().build();
    }
}
