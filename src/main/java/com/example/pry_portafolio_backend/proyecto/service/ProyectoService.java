package com.example.pry_portafolio_backend.proyecto.service;

import com.example.pry_portafolio_backend.proyecto.dto.ProyectoRequest;
import com.example.pry_portafolio_backend.proyecto.dto.ProyectoResponse;

import java.util.List;

public interface ProyectoService {
    List<ProyectoResponse> listarProyectos();
    List<ProyectoResponse> listarProyectosPorProgramador(String email);
    ProyectoResponse obtenerProyecto(Integer id);
    ProyectoResponse crearProyecto(ProyectoRequest request);
    ProyectoResponse actualizarProyecto(Integer id, ProyectoRequest request);
    void eliminarProyecto(Integer id);
}
