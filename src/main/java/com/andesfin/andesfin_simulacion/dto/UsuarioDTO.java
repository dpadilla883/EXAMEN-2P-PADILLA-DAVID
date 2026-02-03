package com.andesfin.andesfin_simulacion.dto;


import java.math.BigDecimal;
import java.util.UUID;

public record UsuarioDTO(UUID id, String nombre, String email, BigDecimal capital_disponible) {}
