package com.example.pry_portafolio_backend.proyecto.service;

import com.example.pry_portafolio_backend.proyecto.dto.ProyectoRequest;
import com.example.pry_portafolio_backend.proyecto.dto.ProyectoResponse;
import com.example.pry_portafolio_backend.proyecto.entity.Proyecto;
import com.example.pry_portafolio_backend.proyecto.repository.ProyectoRepository;
import com.example.pry_portafolio_backend.usuario.entity.Usuario;
import com.example.pry_portafolio_backend.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProyectoServiceImpl implements ProyectoService {

    private final ProyectoRepository proyectoRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public List<ProyectoResponse> listarProyectos() {
        return proyectoRepository.findByActivoTrue().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<ProyectoResponse> listarProyectosPorProgramador(String email) {
        Usuario programador = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Programador no encontrado"));
        
        return proyectoRepository.findByProgramadorAndActivoTrue(programador).stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ProyectoResponse obtenerProyecto(Integer id) {
        Proyecto proyecto = proyectoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
        return mapToResponse(proyecto);
    }

    @Override
    @Transactional
    public ProyectoResponse crearProyecto(ProyectoRequest request) {
        Usuario programador = usuarioRepository.findByEmail(request.programadorEmail())
                .orElseThrow(() -> new RuntimeException("Programador no encontrado"));

        Proyecto proyecto = new Proyecto();
        proyecto.setProgramador(programador);
        proyecto.setCategoria(request.categoria());
        proyecto.setNombre(request.nombre());
        proyecto.setDescripcion(request.descripcion());
        proyecto.setTecnologiasUsadas(request.tecnologiasUsadas());
        proyecto.setUrlRepositorio(request.urlRepositorio());
        proyecto.setUrlDespliegue(request.urlDespliegue());
        proyecto.setUrlImagenPreview(request.urlImagenPreview());
        proyecto.setActivo(true);

        return mapToResponse(proyectoRepository.save(proyecto));
    }

    @Override
    @Transactional
    public ProyectoResponse actualizarProyecto(Integer id, ProyectoRequest request) {
        Proyecto proyecto = proyectoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

        proyecto.setCategoria(request.categoria());
        proyecto.setNombre(request.nombre());
        proyecto.setDescripcion(request.descripcion());
        proyecto.setTecnologiasUsadas(request.tecnologiasUsadas());
        proyecto.setUrlRepositorio(request.urlRepositorio());
        proyecto.setUrlDespliegue(request.urlDespliegue());
        proyecto.setUrlImagenPreview(request.urlImagenPreview());

        return mapToResponse(proyectoRepository.save(proyecto));
    }

    @Override
    @Transactional
    public void eliminarProyecto(Integer id) {
        Proyecto proyecto = proyectoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
        proyecto.setActivo(false);
        proyectoRepository.save(proyecto);
    }

    private ProyectoResponse mapToResponse(Proyecto proyecto) {
        return new ProyectoResponse(
                proyecto.getId(),
                proyecto.getProgramador().getEmail(),
                proyecto.getCategoria(),
                proyecto.getNombre(),
                proyecto.getDescripcion(),
                proyecto.getTecnologiasUsadas(),
                proyecto.getUrlRepositorio(),
                proyecto.getUrlDespliegue(),
                proyecto.getUrlImagenPreview(),
                proyecto.getActivo()
        );
    }
}
