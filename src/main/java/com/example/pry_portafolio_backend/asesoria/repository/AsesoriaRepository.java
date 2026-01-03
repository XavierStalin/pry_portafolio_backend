package com.example.pry_portafolio_backend.asesoria.repository;

import com.example.pry_portafolio_backend.asesoria.entity.Asesoria;
import com.example.pry_portafolio_backend.usuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AsesoriaRepository extends JpaRepository<Asesoria, Integer> {
    List<Asesoria> findByProgramador(Usuario programador);
    List<Asesoria> findByCliente(Usuario cliente);
}
