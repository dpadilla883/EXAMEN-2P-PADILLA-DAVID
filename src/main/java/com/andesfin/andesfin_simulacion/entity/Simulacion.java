package com.andesfin.andesfin_simulacion.entity;



import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "simulaciones")
public class Simulacion {

    @Id
    @Column(nullable = false)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(name = "fecha_simulacion", nullable = false)
    private OffsetDateTime fechaSimulacion;

    @Column(name = "capital_disponible", nullable = false, precision = 10, scale = 2)
    private BigDecimal capitalDisponible;

    @Column(name = "costo_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal costoTotal;

    @Column(name = "capital_restante", nullable = false, precision = 10, scale = 2)
    private BigDecimal capitalRestante;

    @Column(name = "ganancia_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal gananciaTotal;

    @Column(name = "retorno_total_porcentaje", nullable = false, precision = 7, scale = 2)
    private BigDecimal retornoTotalPorcentaje;

    @Column(name = "riesgo_promedio", nullable = false, precision = 7, scale = 2)
    private BigDecimal riesgoPromedio;

    @OneToMany(mappedBy = "simulacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SimulacionDetalle> detalles = new ArrayList<>();

    public Simulacion() { }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public OffsetDateTime getFechaSimulacion() { return fechaSimulacion; }
    public void setFechaSimulacion(OffsetDateTime fechaSimulacion) { this.fechaSimulacion = fechaSimulacion; }

    public BigDecimal getCapitalDisponible() { return capitalDisponible; }
    public void setCapitalDisponible(BigDecimal capitalDisponible) { this.capitalDisponible = capitalDisponible; }

    public BigDecimal getCostoTotal() { return costoTotal; }
    public void setCostoTotal(BigDecimal costoTotal) { this.costoTotal = costoTotal; }

    public BigDecimal getCapitalRestante() { return capitalRestante; }
    public void setCapitalRestante(BigDecimal capitalRestante) { this.capitalRestante = capitalRestante; }

    public BigDecimal getGananciaTotal() { return gananciaTotal; }
    public void setGananciaTotal(BigDecimal gananciaTotal) { this.gananciaTotal = gananciaTotal; }

    public BigDecimal getRetornoTotalPorcentaje() { return retornoTotalPorcentaje; }
    public void setRetornoTotalPorcentaje(BigDecimal retornoTotalPorcentaje) { this.retornoTotalPorcentaje = retornoTotalPorcentaje; }

    public BigDecimal getRiesgoPromedio() { return riesgoPromedio; }
    public void setRiesgoPromedio(BigDecimal riesgoPromedio) { this.riesgoPromedio = riesgoPromedio; }

    public List<SimulacionDetalle> getDetalles() { return detalles; }
    public void setDetalles(List<SimulacionDetalle> detalles) { this.detalles = detalles; }
}
