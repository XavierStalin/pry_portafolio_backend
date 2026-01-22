package com.example.pry_portafolio_backend.usuario.controller;

import com.example.pry_portafolio_backend.usuario.dto.UsuarioRequest;
import com.example.pry_portafolio_backend.usuario.dto.UsuarioResponse;
import com.example.pry_portafolio_backend.usuario.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // OBTENER datos basicos de un usuario
    @GetMapping("/{email}")
    public ResponseEntity<UsuarioResponse> obtenerUno(@PathVariable String email) {
        return ResponseEntity.ok(usuarioService.buscarUsuario(email));
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> crearUsuario(@RequestBody UsuarioRequest request) {
        return ResponseEntity.ok(usuarioService.guardarUsuario(request));
    }

}
