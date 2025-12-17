package com.example.pry_portafolio_backend.obj_acceso_datos;

import com.example.pry_portafolio_backend.entidades_negocio.Disponibilidad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisponibilidadRepository extends JpaRepository<Disponibilidad, Integer> {
}
