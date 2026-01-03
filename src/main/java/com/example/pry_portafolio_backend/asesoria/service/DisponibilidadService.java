package com.example.pry_portafolio_backend.asesoria.service;

import com.example.pry_portafolio_backend.asesoria.dto.DisponibilidadRequest;
import com.example.pry_portafolio_backend.asesoria.dto.DisponibilidadResponse;

import java.util.List;

public interface DisponibilidadService {
    List<DisponibilidadResponse> listarDisponibilidades();
    List<DisponibilidadResponse> listarPorProgramador(String email);
    DisponibilidadResponse crearDisponibilidad(DisponibilidadRequest request);
    DisponibilidadResponse actualizarDisponibilidad(Integer id, DisponibilidadRequest request);
    void eliminarDisponibilidad(Integer id);
}
