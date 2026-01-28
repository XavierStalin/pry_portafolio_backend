package com.example.pry_portafolio_backend.asesoria.service;

import com.example.pry_portafolio_backend.asesoria.dto.AsesoriaRequest;
import com.example.pry_portafolio_backend.asesoria.dto.AsesoriaResponse;
import com.example.pry_portafolio_backend.asesoria.entity.AdvisoryStatus;

import java.util.List;

public interface AsesoriaService {
    List<AsesoriaResponse> listarAsesorias();
    List<AsesoriaResponse> listarAsesoriasPorProgramador(String email);
    List<AsesoriaResponse> listarAsesoriasPorCliente(String email);
    AsesoriaResponse obtenerAsesoria(Integer id);
    AsesoriaResponse solicitarAsesoria(AsesoriaRequest request);
    AsesoriaResponse responderAsesoria(Integer id, String mensaje, String link, AdvisoryStatus estado);
    AsesoriaResponse actualizarAsesoria(Integer id, AsesoriaRequest request);
    void eliminarAsesoria(Integer id);
}
