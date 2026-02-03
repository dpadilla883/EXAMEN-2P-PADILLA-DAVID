package com.andesfin.andesfin_simulacion.entity;



import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false, length = 120)
    private String nombre;

    @Column(nullable = false, unique = true, length = 160)
    private String email;

    @Column(name = "capital_disponible", nullable = false, precision = 10, scale = 2)
    private BigDecimal capitalDisponible;

    public Usuario() { }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public BigDecimal getCapitalDisponible() { return capitalDisponible; }
    public void setCapitalDisponible(BigDecimal capitalDisponible) { this.capitalDisponible = capitalDisponible; }
}
