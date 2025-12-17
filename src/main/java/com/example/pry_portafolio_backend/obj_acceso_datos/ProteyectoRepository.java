package com.example.pry_portafolio_backend.obj_acceso_datos;

import com.example.pry_portafolio_backend.entidades_negocio.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProteyectoRepository extends JpaRepository<Proyecto, Integer> {
}
