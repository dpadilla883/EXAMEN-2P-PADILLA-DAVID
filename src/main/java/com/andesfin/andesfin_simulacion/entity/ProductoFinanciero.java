
package com.andesfin.andesfin_simulacion.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "productos_financieros")
public class ProductoFinanciero {

    @Id
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false, length = 140)
    private String nombre;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal costo;

    @Column(name = "porcentaje_retorno", nullable = false, precision = 7, scale = 2)
    private BigDecimal porcentajeRetorno;

    @Column(nullable = false)
    private Boolean activo;

    public ProductoFinanciero() { }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public BigDecimal getCosto() { return costo; }
    public void setCosto(BigDecimal costo) { this.costo = costo; }

    public BigDecimal getPorcentajeRetorno() { return porcentajeRetorno; }
    public void setPorcentajeRetorno(BigDecimal porcentajeRetorno) { this.porcentajeRetorno = porcentajeRetorno; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}
