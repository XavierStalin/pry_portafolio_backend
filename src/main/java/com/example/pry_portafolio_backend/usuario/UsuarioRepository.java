package com.example.pry_portafolio_backend.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByEmail(String email);

    boolean existsUsuarioByEmail(String email);

    List<Usuario> findUsuariosByActivo(Boolean activo);
}
