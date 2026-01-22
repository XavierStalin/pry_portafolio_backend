package com.example.pry_portafolio_backend.asesoria.service;

import com.example.pry_portafolio_backend.asesoria.dto.AsesoriaRequest;
import com.example.pry_portafolio_backend.asesoria.dto.AsesoriaResponse;
import com.example.pry_portafolio_backend.asesoria.entity.Asesoria;
import com.example.pry_portafolio_backend.asesoria.entity.AdvisoryStatus;
import com.example.pry_portafolio_backend.asesoria.repository.AsesoriaRepository;
import com.example.pry_portafolio_backend.usuario.entity.Usuario;
import com.example.pry_portafolio_backend.usuario.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AsesoriaServiceImpl implements AsesoriaService {

    private final AsesoriaRepository asesoriaRepository;
    private final UsuarioRepository usuarioRepository;

    public AsesoriaServiceImpl(AsesoriaRepository asesoriaRepository, UsuarioRepository usuarioRepository) {
        this.asesoriaRepository = asesoriaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<AsesoriaResponse> listarAsesorias() {
        return asesoriaRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<AsesoriaResponse> listarAsesoriasPorProgramador(String email) {
        Usuario programador = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Programador no encontrado"));
        return asesoriaRepository.findByProgramador(programador).stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<AsesoriaResponse> listarAsesoriasPorCliente(String email) {
        Usuario cliente = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        return asesoriaRepository.findByCliente(cliente).stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public AsesoriaResponse obtenerAsesoria(Integer id) {
        Asesoria asesoria = asesoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asesoría no encontrada"));
        return mapToResponse(asesoria);
    }

    @Override
    @Transactional
    public AsesoriaResponse solicitarAsesoria(AsesoriaRequest request) {
        Usuario cliente = usuarioRepository.findByEmail(request.clienteEmail())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        Usuario programador = usuarioRepository.findByEmail(request.programadorEmail())
                .orElseThrow(() -> new RuntimeException("Programador no encontrado"));

        Asesoria asesoria = new Asesoria();
        asesoria.setCliente(cliente);
        asesoria.setProgramador(programador);
        asesoria.setFechaHoraInicio(request.fechaHoraInicio());
        asesoria.setMotivoConsulta(request.motivoConsulta());
        asesoria.setDuracionMinutos(request.duracionMinutos() != null ? request.duracionMinutos() : 60);
        asesoria.setEstado(AdvisoryStatus.PENDIENTE);

        return mapToResponse(asesoriaRepository.save(asesoria));
    }

    @Override
    @Transactional
    public AsesoriaResponse responderAsesoria(Integer id, String mensaje, String link, AdvisoryStatus estado) {
        Asesoria asesoria = asesoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asesoría no encontrada"));

        asesoria.setMensajeRespuesta(mensaje);
        asesoria.setLinkReunion(link);
        asesoria.setEstado(estado);

        return mapToResponse(asesoriaRepository.save(asesoria));
    }

    @Override
    @Transactional
    public void eliminarAsesoria(Integer id) {
        asesoriaRepository.deleteById(id);
    }

    private AsesoriaResponse mapToResponse(Asesoria asesoria) {
        return new AsesoriaResponse(
                asesoria.getId(),
                asesoria.getCliente().getEmail(),
                asesoria.getProgramador().getEmail(),
                asesoria.getFechaHoraInicio(),
                asesoria.getMotivoConsulta(),
                asesoria.getEstado(),
                asesoria.getMensajeRespuesta(),
                asesoria.getDuracionMinutos(),
                asesoria.getLinkReunion()
        );
    }
}
