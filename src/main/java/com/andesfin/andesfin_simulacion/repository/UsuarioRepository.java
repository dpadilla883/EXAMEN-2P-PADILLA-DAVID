package com.andesfin.andesfin_simulacion.repository;



import com.andesfin.andesfin_simulacion.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {}
