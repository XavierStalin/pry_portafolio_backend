package com.example.pry_portafolio_backend.usuario.repository;

import com.example.pry_portafolio_backend.usuario.dto.ProgramadorRequest;
import com.example.pry_portafolio_backend.usuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.OptionalInt;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findById(Integer id);

    boolean existsByEmail(String email);

}
