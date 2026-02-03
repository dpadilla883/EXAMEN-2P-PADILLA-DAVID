package com.andesfin.andesfin_simulacion.dto;


import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record SimulacionRequestDTO(
        @NotNull UUID usuario_id,
        @NotNull @DecimalMin("0.00") BigDecimal capital_disponible,
        @NotEmpty List<ProductoCandidatoDTO> productos
) {
    public record ProductoCandidatoDTO(
            @NotBlank String nombre,
            @NotNull @DecimalMin("0.00") BigDecimal precio,
            @NotNull @DecimalMin("0.00") BigDecimal riesgo,
            @NotNull @DecimalMin("0.00") BigDecimal porcentaje_ganancia
    ) {}
}
