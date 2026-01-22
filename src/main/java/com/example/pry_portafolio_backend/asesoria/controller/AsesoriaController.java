package com.example.pry_portafolio_backend.asesoria.controller;

import com.example.pry_portafolio_backend.asesoria.dto.AsesoriaRequest;
import com.example.pry_portafolio_backend.asesoria.dto.AsesoriaResponse;
import com.example.pry_portafolio_backend.asesoria.service.AsesoriaService;
import com.example.pry_portafolio_backend.asesoria.entity.AdvisoryStatus;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asesorias")
public class AsesoriaController {

    private final AsesoriaService asesoriaService;

    public AsesoriaController(AsesoriaService asesoriaService) {
        this.asesoriaService = asesoriaService;
    }

    @GetMapping
    public ResponseEntity<List<AsesoriaResponse>> listar() {
        return ResponseEntity.ok(asesoriaService.listarAsesorias());
    }

    @GetMapping("/programador/{email}")
    public ResponseEntity<List<AsesoriaResponse>> listarPorProgramador(@PathVariable String email) {
        return ResponseEntity.ok(asesoriaService.listarAsesoriasPorProgramador(email));
    }

    @GetMapping("/cliente/{email}")
    public ResponseEntity<List<AsesoriaResponse>> listarPorCliente(@PathVariable String email) {
        return ResponseEntity.ok(asesoriaService.listarAsesoriasPorCliente(email));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AsesoriaResponse> obtenerUno(@PathVariable Integer id) {
        return ResponseEntity.ok(asesoriaService.obtenerAsesoria(id));
    }

    @PostMapping
    public ResponseEntity<AsesoriaResponse> solicitar(@Valid @RequestBody AsesoriaRequest request) {
        return new ResponseEntity<>(asesoriaService.solicitarAsesoria(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/responder")
    public ResponseEntity<AsesoriaResponse> responder(
            @PathVariable Integer id,
            @RequestParam String mensaje,
            @RequestParam String link,
            @RequestParam AdvisoryStatus estado) {
        return ResponseEntity.ok(asesoriaService.responderAsesoria(id, mensaje, link, estado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        asesoriaService.eliminarAsesoria(id);
        return ResponseEntity.noContent().build();
    }
}
