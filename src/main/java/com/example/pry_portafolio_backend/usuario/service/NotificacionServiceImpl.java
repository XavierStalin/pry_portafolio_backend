package com.example.pry_portafolio_backend.usuario.service;

import com.example.pry_portafolio_backend.usuario.dto.NotificacionRequest;
import com.example.pry_portafolio_backend.usuario.dto.NotificacionResponse;
import com.example.pry_portafolio_backend.usuario.entity.Notificacion;
import com.example.pry_portafolio_backend.usuario.entity.Usuario;
import com.example.pry_portafolio_backend.usuario.repository.NotificacionRepository;
import com.example.pry_portafolio_backend.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificacionServiceImpl implements NotificacionService {

    private final NotificacionRepository notificacionRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public NotificacionResponse guardarNotificacion(NotificacionRequest request) {
        Usuario usuario = usuarioRepository.findById(request.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + request.usuarioId()));

        Notificacion notificacion = Notificacion.builder()
                .usuario(usuario)
                .tipo(request.tipo())
                .mensaje(request.mensaje())
                .exitoso(request.exitoso() != null ? request.exitoso() : true)
                .build();

        return mapToResponse(notificacionRepository.save(notificacion));
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificacionResponse> listarNotificaciones() {
        return notificacionRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public NotificacionResponse buscarPorId(Integer id) {
        return notificacionRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada con ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificacionResponse> listarPorUsuario(Integer usuarioId) {
        // Asumiendo que existe el método en el repositorio, si no lo crearemos luego
        return notificacionRepository.findByUsuarioId(usuarioId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public NotificacionResponse actualizarNotificacion(Integer id, NotificacionRequest request) {
        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada con ID: " + id));

        if (request.usuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(request.usuarioId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + request.usuarioId()));
            notificacion.setUsuario(usuario);
        }
        
        if (request.tipo() != null) {
            notificacion.setTipo(request.tipo());
        }
        
        if (request.mensaje() != null) {
            notificacion.setMensaje(request.mensaje());
        }
        
        if (request.exitoso() != null) {
            notificacion.setExitoso(request.exitoso());
        }

        return mapToResponse(notificacionRepository.save(notificacion));
    }

    @Override
    @Transactional
    public void eliminarNotificacion(Integer id) {
        if (!notificacionRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: Notificación no encontrada");
        }
        notificacionRepository.deleteById(id);
    }

    private NotificacionResponse mapToResponse(Notificacion notificacion) {
        return new NotificacionResponse(
                notificacion.getId(),
                notificacion.getUsuario().getId(),
                notificacion.getUsuario().getEmail(),
                notificacion.getTipo(),
                notificacion.getMensaje(),
                notificacion.getFechaEnvio(),
                notificacion.getExitoso()
        );
    }
}
