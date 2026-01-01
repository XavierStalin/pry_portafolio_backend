package com.example.pry_portafolio_backend.usuario.repository;

import com.example.pry_portafolio_backend.usuario.entity.ProgramadorDetalle;
import com.example.pry_portafolio_backend.usuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProgramadorDetalleRepository extends JpaRepository<ProgramadorDetalle, Integer> {
    Optional<ProgramadorDetalle> findByUsuario(Usuario usuario);

    List<ProgramadorDetalle> findByActivo(Boolean activo);

    boolean existsByUsuario(Usuario usuario);
}
