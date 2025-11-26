package com.hotel.hotel_backend.Controller;


import java.util.List;
import com.hotel.hotel_backend.dao.HuespedDAOEnMemoria;
import com.hotel.hotel_backend.domain.Huesped;
import com.hotel.hotel_backend.service.HuespedService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/huespedes")
public class HuespedController {

    private final HuespedService service;

    public HuespedController() {
        // TEMPORAL: Más adelante esto se reemplaza con @Service + @Repository + @Autowired
        this.service = new HuespedService(HuespedDAOEnMemoria.getInstance());
    }

    // CU: Dar alta huésped
    @PostMapping
    public String crear(@RequestBody Huesped h) {
        service.darAlta(h);
        return "Huesped creado correctamente";
    }

    // Listar todos (para probar)
    @GetMapping
    public List<Huesped> listar() {
        return service.listar();
    }

    @GetMapping("/buscar")
    public List<Huesped> buscar(
            @RequestParam(required = false) String apellido,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String tipoDocumento,
            @RequestParam(required = false) String numeroDocumento
    ) {
        return service.buscarConFiltros(apellido, nombre, tipoDocumento, numeroDocumento);
    }

    @PutMapping("/modificar")
    public ResponseEntity<Huesped> modificar(@RequestBody Huesped cambios) {
        Huesped modificado = service.modificarHuesped(cambios);
        return ResponseEntity.ok(modificado);
    }




}
