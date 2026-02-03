package com.andesfin.andesfin_simulacion.service;



import com.andesfin.andesfin_simulacion.dto.UsuarioDTO;
import com.andesfin.andesfin_simulacion.entity.Usuario;
import com.andesfin.andesfin_simulacion.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repo;

    public UsuarioService(UsuarioRepository repo) {
        this.repo = repo;
    }

    public List<UsuarioDTO> listar() {
        return repo.findAll().stream()
                .map(u -> new UsuarioDTO(u.getId(), u.getNombre(), u.getEmail(), u.getCapitalDisponible()))
                .toList();
    }
}
