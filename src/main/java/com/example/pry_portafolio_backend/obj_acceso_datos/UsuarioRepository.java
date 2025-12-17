package com.example.pry_portafolio_backend.obj_acceso_datos;

import com.example.pry_portafolio_backend.entidades_negocio.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    boolean existsUsuarioByEmail(String email);

    List<Usuario> findUsuariosByActivo(Boolean activo);
}
