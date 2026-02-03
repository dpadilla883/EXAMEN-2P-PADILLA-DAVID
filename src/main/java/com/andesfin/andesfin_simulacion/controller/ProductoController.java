package com.andesfin.andesfin_simulacion.controller;

import com.andesfin.andesfin_simulacion.dto.ProductoDTO;
import com.andesfin.andesfin_simulacion.service.ProductoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProductoDTO> listarActivos() {
        return service.listarActivos();
    }
}
