package com.example.pry_portafolio_backend.asesoria.repository;

import com.example.pry_portafolio_backend.asesoria.entity.Disponibilidad;
import com.example.pry_portafolio_backend.usuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DisponibilidadRepository extends JpaRepository<Disponibilidad, Integer> {
    List<Disponibilidad> findByProgramador(Usuario programador);
}
