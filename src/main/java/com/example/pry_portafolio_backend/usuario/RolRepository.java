package com.example.pry_portafolio_backend.usuario;

import com.example.pry_portafolio_backend.entidades_negocio.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Integer> {

    Optional<Rol> findByNombre(String nombre);
}
