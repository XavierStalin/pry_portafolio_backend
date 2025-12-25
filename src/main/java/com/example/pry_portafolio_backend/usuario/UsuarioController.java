package com.example.pry_portafolio_backend.usuario;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aut")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable Integer id) {
        Usuario usuario = usuarioService.obtenerUsuario(id);
        return ResponseEntity.ok(usuario);
    }

//    @PostMapping("/login")
//    public ResponseEntity<Usuario> crearUsuario(@RequestBody final UsuarioResponse datos) {
//        final Usuario nuevoUsuario = usuarioService.crearUsuario(datos);
//        return ResponseEntity.ok(nuevoUsuario);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
