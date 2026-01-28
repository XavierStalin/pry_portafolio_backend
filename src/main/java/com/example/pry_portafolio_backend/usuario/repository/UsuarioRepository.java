package com.example.pry_portafolio_backend.usuario.repository;


import com.example.pry_portafolio_backend.usuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.OptionalInt;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
    //Ya se incluye todo esto en JPA (No es necesario pero tampoco duele tenerlo)
    //Optional<Usuario> findById(Integer id);



}
