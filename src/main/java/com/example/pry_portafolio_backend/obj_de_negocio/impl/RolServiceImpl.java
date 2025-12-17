package com.example.pry_portafolio_backend.obj_de_negocio.impl;

import com.example.pry_portafolio_backend.entidades_negocio.Rol;
import com.example.pry_portafolio_backend.obj_acceso_datos.RolRepository;
import com.example.pry_portafolio_backend.obj_de_negocio.RolService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;

    @Override
    public Rol traerRol(String rol) {
        return rolRepository.findByNombre(rol);
    }

}
