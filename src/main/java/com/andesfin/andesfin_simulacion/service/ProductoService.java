package com.andesfin.andesfin_simulacion.service;

import com.andesfin.andesfin_simulacion.dto.ProductoDTO;
import com.andesfin.andesfin_simulacion.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository repo;

    public ProductoService(ProductoRepository repo) {
        this.repo = repo;
    }

    public List<ProductoDTO> listarActivos() {
        return repo.findByActivoTrue().stream()
                .map(p -> new ProductoDTO(p.getId(), p.getNombre(), p.getDescripcion(), p.getCosto(), p.getPorcentajeRetorno(), p.getActivo()))
                .toList();
    }
}
