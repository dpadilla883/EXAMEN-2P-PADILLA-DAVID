package com.andesfin.andesfin_simulacion.controller;


import com.andesfin.andesfin_simulacion.dto.SimulacionRequestDTO;
import com.andesfin.andesfin_simulacion.dto.SimulacionResponseDTO;
import com.andesfin.andesfin_simulacion.service.SimulacionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/simulaciones")
public class SimulacionController {

    private final SimulacionService service;

    public SimulacionController(SimulacionService service) {
        this.service = service;
    }

    @PostMapping
    public SimulacionResponseDTO simular(@Valid @RequestBody SimulacionRequestDTO req) {
        return service.simular(req);
    }

    @GetMapping("/{usuarioId}")
    public List<Map<String, Object>> listarPorUsuario(@PathVariable UUID usuarioId) {
        return service.listarPorUsuario(usuarioId);
    }
}
