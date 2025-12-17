package com.example.pry_portafolio_backend.fachada;

import com.example.pry_portafolio_backend.dto.RegistroUsuarioDTO;
import com.example.pry_portafolio_backend.entidades_negocio.Usuario;
import com.example.pry_portafolio_backend.obj_de_negocio.UsuarioService;
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

    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody RegistroUsuarioDTO datos) {
        Usuario nuevoUsuario = usuarioService.crearUsuario(datos);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
