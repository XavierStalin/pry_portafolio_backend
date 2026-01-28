package com.example.pry_portafolio_backend.usuario.controller;

import com.example.pry_portafolio_backend.usuario.dto.UsuarioRequest;
import com.example.pry_portafolio_backend.usuario.dto.UsuarioResponse;
import com.example.pry_portafolio_backend.usuario.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    // 1. CREATE
    @PostMapping
    public ResponseEntity<UsuarioResponse> crearUsuario(@RequestBody UsuarioRequest request) {
        return new ResponseEntity<>(usuarioService.guardarUsuario(request), HttpStatus.CREATED);
    }

    // 2. READ (Todos)
    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> obtenerTodos() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    // 3. READ (Por Email - El que ya tenías)
    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioResponse> obtenerPorEmail(@PathVariable String email) {
        return ResponseEntity.ok(usuarioService.buscarUsuario(email));
    }

    // 4. READ (Por ID)
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    // 5. UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> actualizarUsuario(
            @PathVariable Integer id,
            @RequestBody UsuarioRequest request) {
        return ResponseEntity.ok(usuarioService.actualizarUsuario(id, request));
    }

    // 6. DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build(); // Devuelve 204 No Content
    }
}