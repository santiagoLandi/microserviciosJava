package com.tienda.microusuario.mapper;

import com.tienda.microusuario.dto.UsuarioDTO;
import com.tienda.microusuario.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {
    public UsuarioDTO toUsuarioDTO(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getTelefono(),
                usuario.getDireccion(),
                usuario.getFechaRegistro()
        );
    }
}
