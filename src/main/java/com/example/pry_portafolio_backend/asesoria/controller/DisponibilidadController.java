package com.example.pry_portafolio_backend.asesoria.controller;

import com.example.pry_portafolio_backend.asesoria.dto.DisponibilidadRequest;
import com.example.pry_portafolio_backend.asesoria.dto.DisponibilidadResponse;
import com.example.pry_portafolio_backend.asesoria.service.DisponibilidadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disponibilidades")
@RequiredArgsConstructor
public class DisponibilidadController {

    private final DisponibilidadService disponibilidadService;

    @GetMapping
    public ResponseEntity<List<DisponibilidadResponse>> listar() {
        return ResponseEntity.ok(disponibilidadService.listarDisponibilidades());
    }

    @GetMapping("/programador/{email}")
    public ResponseEntity<List<DisponibilidadResponse>> listarPorProgramador(@PathVariable String email) {
        return ResponseEntity.ok(disponibilidadService.listarPorProgramador(email));
    }

    @PostMapping
    public ResponseEntity<DisponibilidadResponse> crear(@Valid @RequestBody DisponibilidadRequest request) {
        return new ResponseEntity<>(disponibilidadService.crearDisponibilidad(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DisponibilidadResponse> actualizar(@PathVariable Integer id, @Valid @RequestBody DisponibilidadRequest request) {
        return ResponseEntity.ok(disponibilidadService.actualizarDisponibilidad(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        disponibilidadService.eliminarDisponibilidad(id);
        return ResponseEntity.noContent().build();
    }
}
