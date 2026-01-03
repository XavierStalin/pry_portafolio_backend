package com.example.pry_portafolio_backend.usuario.controller;

import com.example.pry_portafolio_backend.usuario.dto.ProgramadorRequest;
import com.example.pry_portafolio_backend.usuario.dto.ProgramadorResponse;
import com.example.pry_portafolio_backend.usuario.dto.UsuarioResponse;
import com.example.pry_portafolio_backend.usuario.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/programadores")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService programadorService;

    // LISTAR
    @GetMapping
    public ResponseEntity<List<ProgramadorResponse>> listar() {
        return ResponseEntity.ok(programadorService.listarProgramadores());
    }

    // CREAR
    @PostMapping
    public ResponseEntity<ProgramadorResponse> crear(@Valid @RequestBody ProgramadorRequest request) {
        // Retornamos 201 Created
        System.out.println("Entrando a crear el programador");
        return new ResponseEntity<>(programadorService.agregarProgramador(request), HttpStatus.CREATED);
    }

    // ACTUALIZAR (DATOS)
    @PutMapping("/{email}")
    public ResponseEntity<ProgramadorResponse> actualizar(
            @PathVariable String email,
            @RequestBody ProgramadorResponse request) {
        return ResponseEntity.ok(programadorService.actualizarProgramador(email, request));
    }

    // OBTENER datos basicos de un usuario al que se quiera convertir en programador
    @GetMapping("/{email}")
    public ResponseEntity<UsuarioResponse> obtenerUno(@PathVariable String email) {
        return ResponseEntity.ok(programadorService.buscarUsuario(email));
    }

    @PutMapping("/desactivar/{email}")
    public void darBajaProgramador(
            @PathVariable String email) {
        programadorService.eliminarProgramador(email);
    }

}
