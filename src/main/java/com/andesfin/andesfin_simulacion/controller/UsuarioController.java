package com.andesfin.andesfin_simulacion.controller;


import com.andesfin.andesfin_simulacion.dto.UsuarioDTO;
import com.andesfin.andesfin_simulacion.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping
    public List<UsuarioDTO> listar() {
        return service.listar();
    }
}
