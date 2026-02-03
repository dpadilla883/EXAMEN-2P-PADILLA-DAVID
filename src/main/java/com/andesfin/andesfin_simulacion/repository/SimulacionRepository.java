package com.andesfin.andesfin_simulacion.repository;



import com.andesfin.andesfin_simulacion.entity.Simulacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface SimulacionRepository extends JpaRepository<Simulacion, UUID> {
    List<Simulacion> findByUsuario_IdOrderByFechaSimulacionDesc(UUID usuarioId);
}
