package com.example.pry_portafolio_backend.usuario.controller;

import com.example.pry_portafolio_backend.usuario.dto.NotificacionRequest;
import com.example.pry_portafolio_backend.usuario.dto.NotificacionResponse;
import com.example.pry_portafolio_backend.usuario.service.NotificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {

    private final NotificacionService notificacionService;

    @PostMapping
    public ResponseEntity<NotificacionResponse> crear(@RequestBody NotificacionRequest request) {
        return new ResponseEntity<>(notificacionService.guardarNotificacion(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<NotificacionResponse>> listar() {
        return ResponseEntity.ok(notificacionService.listarNotificaciones());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificacionResponse> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(notificacionService.buscarPorId(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<NotificacionResponse>> listarPorUsuario(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(notificacionService.listarPorUsuario(usuarioId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificacionResponse> actualizar(
            @PathVariable Integer id,
            @RequestBody NotificacionRequest request) {
        return ResponseEntity.ok(notificacionService.actualizarNotificacion(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        notificacionService.eliminarNotificacion(id);
        return ResponseEntity.noContent().build();
    }
}
