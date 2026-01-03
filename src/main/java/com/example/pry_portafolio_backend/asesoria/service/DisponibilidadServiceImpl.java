package com.example.pry_portafolio_backend.asesoria.service;

import com.example.pry_portafolio_backend.asesoria.dto.DisponibilidadRequest;
import com.example.pry_portafolio_backend.asesoria.dto.DisponibilidadResponse;
import com.example.pry_portafolio_backend.asesoria.entity.Disponibilidad;
import com.example.pry_portafolio_backend.asesoria.repository.DisponibilidadRepository;
import com.example.pry_portafolio_backend.usuario.entity.Usuario;
import com.example.pry_portafolio_backend.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DisponibilidadServiceImpl implements DisponibilidadService {

    private final DisponibilidadRepository disponibilidadRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public List<DisponibilidadResponse> listarDisponibilidades() {
        return disponibilidadRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<DisponibilidadResponse> listarPorProgramador(String email) {
        Usuario programador = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Programador no encontrado"));
        return disponibilidadRepository.findByProgramador(programador).stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional
    public DisponibilidadResponse crearDisponibilidad(DisponibilidadRequest request) {
        Usuario programador = usuarioRepository.findByEmail(request.programadorEmail())
                .orElseThrow(() -> new RuntimeException("Programador no encontrado"));

        Disponibilidad disponibilidad = new Disponibilidad();
        disponibilidad.setProgramador(programador);
        disponibilidad.setDiaSemana(request.diaSemana());
        disponibilidad.setHoraInicio(request.horaInicio());
        disponibilidad.setHoraFin(request.horaFin());
        disponibilidad.setModalidad(request.modalidad());

        return mapToResponse(disponibilidadRepository.save(disponibilidad));
    }

    @Override
    @Transactional
    public DisponibilidadResponse actualizarDisponibilidad(Integer id, DisponibilidadRequest request) {
        Disponibilidad disponibilidad = disponibilidadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Disponibilidad no encontrada"));

        disponibilidad.setDiaSemana(request.diaSemana());
        disponibilidad.setHoraInicio(request.horaInicio());
        disponibilidad.setHoraFin(request.horaFin());
        disponibilidad.setModalidad(request.modalidad());

        return mapToResponse(disponibilidadRepository.save(disponibilidad));
    }

    @Override
    @Transactional
    public void eliminarDisponibilidad(Integer id) {
        disponibilidadRepository.deleteById(id);
    }

    private DisponibilidadResponse mapToResponse(Disponibilidad disponibilidad) {
        return new DisponibilidadResponse(
                disponibilidad.getId(),
                disponibilidad.getProgramador().getEmail(),
                disponibilidad.getDiaSemana(),
                disponibilidad.getHoraInicio(),
                disponibilidad.getHoraFin(),
                disponibilidad.getModalidad()
        );
    }
}
