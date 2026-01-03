package com.example.pry_portafolio_backend.proyecto.controller;

import com.example.pry_portafolio_backend.proyecto.dto.ProyectoRequest;
import com.example.pry_portafolio_backend.proyecto.dto.ProyectoResponse;
import com.example.pry_portafolio_backend.proyecto.service.ProyectoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proyectos")
@RequiredArgsConstructor
public class ProyectoController {

    private final ProyectoService proyectoService;

    @GetMapping
    public ResponseEntity<List<ProyectoResponse>> listar() {
        return ResponseEntity.ok(proyectoService.listarProyectos());
    }

    @GetMapping("/programador/{email}")
    public ResponseEntity<List<ProyectoResponse>> listarPorProgramador(@PathVariable String email) {
        return ResponseEntity.ok(proyectoService.listarProyectosPorProgramador(email));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProyectoResponse> obtenerUno(@PathVariable Integer id) {
        return ResponseEntity.ok(proyectoService.obtenerProyecto(id));
    }

    @PostMapping
    public ResponseEntity<ProyectoResponse> crear(@Valid @RequestBody ProyectoRequest request) {
        return new ResponseEntity<>(proyectoService.crearProyecto(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProyectoResponse> actualizar(@PathVariable Integer id, @Valid @RequestBody ProyectoRequest request) {
        return ResponseEntity.ok(proyectoService.actualizarProyecto(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        proyectoService.eliminarProyecto(id);
        return ResponseEntity.noContent().build();
    }
}
