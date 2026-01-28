package com.example.pry_portafolio_backend.usuario.service;

import com.example.pry_portafolio_backend.usuario.dto.NotificacionRequest;
import com.example.pry_portafolio_backend.usuario.dto.NotificacionResponse;
import java.util.List;

public interface NotificacionService {
    NotificacionResponse guardarNotificacion(NotificacionRequest request);
    List<NotificacionResponse> listarNotificaciones();
    NotificacionResponse buscarPorId(Integer id);
    List<NotificacionResponse> listarPorUsuario(Integer usuarioId);
    NotificacionResponse actualizarNotificacion(Integer id, NotificacionRequest request);
    void eliminarNotificacion(Integer id);
}
