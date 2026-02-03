package com.andesfin.andesfin_simulacion.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record SimulacionResponseDTO(
        UUID id,
        UUID usuario_id,
        OffsetDateTime fecha_simulacion,
        BigDecimal capital_disponible,
        List<ProductoSeleccionadoDTO> productos_seleccionados,
        BigDecimal costo_total,
        BigDecimal capital_restante,
        BigDecimal ganancia_total,
        BigDecimal retorno_total_porcentaje,
        BigDecimal riesgo_promedio,
        BigDecimal eficiencia_capital,
        String mensaje
) {
    public record ProductoSeleccionadoDTO(
            String nombre,
            BigDecimal precio,
            BigDecimal riesgo,
            BigDecimal porcentaje_ganancia,
            BigDecimal ganancia_esperada
    ) {}
}
