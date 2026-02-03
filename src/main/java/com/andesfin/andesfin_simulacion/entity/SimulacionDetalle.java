package com.andesfin.andesfin_simulacion.entity;



import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "simulacion_detalle")
public class SimulacionDetalle {

    @Id
    @Column(nullable = false)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "simulacion_id")
    private Simulacion simulacion;

    @Column(name = "producto_nombre", nullable = false, length = 140)
    private String productoNombre;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(nullable = false, precision = 7, scale = 2)
    private BigDecimal riesgo;

    @Column(name = "porcentaje_ganancia", nullable = false, precision = 7, scale = 2)
    private BigDecimal porcentajeGanancia;

    @Column(name = "ganancia_esperada", nullable = false, precision = 10, scale = 2)
    private BigDecimal gananciaEsperada;

    @Column(nullable = false)
    private Boolean seleccionado;

    public SimulacionDetalle() { }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public Simulacion getSimulacion() { return simulacion; }
    public void setSimulacion(Simulacion simulacion) { this.simulacion = simulacion; }

    public String getProductoNombre() { return productoNombre; }
    public void setProductoNombre(String productoNombre) { this.productoNombre = productoNombre; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public BigDecimal getRiesgo() { return riesgo; }
    public void setRiesgo(BigDecimal riesgo) { this.riesgo = riesgo; }

    public BigDecimal getPorcentajeGanancia() { return porcentajeGanancia; }
    public void setPorcentajeGanancia(BigDecimal porcentajeGanancia) { this.porcentajeGanancia = porcentajeGanancia; }

    public BigDecimal getGananciaEsperada() { return gananciaEsperada; }
    public void setGananciaEsperada(BigDecimal gananciaEsperada) { this.gananciaEsperada = gananciaEsperada; }

    public Boolean getSeleccionado() { return seleccionado; }
    public void setSeleccionado(Boolean seleccionado) { this.seleccionado = seleccionado; }
}
