package com.hotelPremier.controller;

import com.hotelPremier.classes.DTO.ListadoIngresosDTO;
import com.hotelPremier.service.IngresoService;
import com.hotelPremier.classes.exception.NegocioException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import java.time.LocalDate;

@RestController
@RequestMapping("/ingresos")
@Tag(name = "Ingresos", description = "Gestión de ingresos")
public class IngresoController {

    @Autowired
    private IngresoService ingresoService;

    @Operation(
        summary = "Listar ingresos",
        description = "Lista los ingresos de un período ordenados por fecha, totalizando por tipo de ingreso y moneda"
    )
    @GetMapping
    public ResponseEntity<ListadoIngresosDTO> listarIngresos(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desdeFecha,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hastaFecha
    ) {
        try {
            ListadoIngresosDTO resultado = ingresoService.listarIngresos(desdeFecha, hastaFecha);
            return ResponseEntity.ok(resultado);
        } catch (NegocioException e) {
            throw e;
        }
    }
}

