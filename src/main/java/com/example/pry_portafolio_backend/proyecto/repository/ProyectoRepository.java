package com.example.pry_portafolio_backend.proyecto.repository;

import com.example.pry_portafolio_backend.proyecto.entity.Proyecto;
import com.example.pry_portafolio_backend.usuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProyectoRepository extends JpaRepository<Proyecto, Integer> {
    List<Proyecto> findByProgramador(Usuario programador);
    List<Proyecto> findByActivoTrue();
    List<Proyecto> findByProgramadorAndActivoTrue(Usuario programador);
}
