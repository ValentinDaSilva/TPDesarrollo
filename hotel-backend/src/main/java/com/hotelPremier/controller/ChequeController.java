package com.hotelPremier.controller;

import com.hotelPremier.classes.DTO.ListadoChequesDTO;
import com.hotelPremier.service.ChequeService;
import com.hotelPremier.classes.exception.NegocioException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import java.util.Date;

@RestController
@RequestMapping("/cheques")
@Tag(name = "Cheques", description = "Gestión de cheques en cartera")
public class ChequeController {

    @Autowired
    private ChequeService chequeService;

    @Operation(
        summary = "Listar cheques",
        description = "Lista los cheques en cartera ordenados por fecha de cobro"
    )
    @GetMapping
    public ResponseEntity<ListadoChequesDTO> listarCheques(
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date desdeFecha,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date hastaFecha
    ) {
        try {
            ListadoChequesDTO resultado = chequeService.listarCheques(desdeFecha, hastaFecha);
            return ResponseEntity.ok(resultado);
        } catch (NegocioException e) {
            throw e;
        }
    }
}

