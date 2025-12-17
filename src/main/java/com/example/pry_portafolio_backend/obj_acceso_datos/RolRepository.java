package com.example.pry_portafolio_backend.obj_acceso_datos;

import com.example.pry_portafolio_backend.entidades_negocio.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol, Integer> {
    Rol findByNombre(String nombre);
}
