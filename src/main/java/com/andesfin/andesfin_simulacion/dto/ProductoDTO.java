package com.andesfin.andesfin_simulacion.dto;


import java.math.BigDecimal;
import java.util.UUID;

public record ProductoDTO(
        UUID id,
        String nombre,
        String descripcion,
        BigDecimal costo,
        BigDecimal porcentaje_retorno,
        Boolean activo
) {}
