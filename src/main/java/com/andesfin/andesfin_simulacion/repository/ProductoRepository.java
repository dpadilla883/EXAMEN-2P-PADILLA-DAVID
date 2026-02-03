package com.andesfin.andesfin_simulacion.repository;



import com.andesfin.andesfin_simulacion.entity.ProductoFinanciero;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface ProductoRepository extends JpaRepository<ProductoFinanciero, UUID> {
    List<ProductoFinanciero> findByActivoTrue();
}
