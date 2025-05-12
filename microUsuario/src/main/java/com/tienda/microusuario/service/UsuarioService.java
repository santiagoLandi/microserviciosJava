package com.tienda.microusuario.service;

import com.tienda.microusuario.dto.UsuarioDTO;
import com.tienda.microusuario.entity.Usuario;
import com.tienda.microusuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {
    private UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public UsuarioDTO save(UsuarioDTO usuario) {
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(usuario.getNombre());
        nuevoUsuario.setApellido(usuario.getApellido());
        nuevoUsuario.setEmail(usuario.getEmail());
        nuevoUsuario.setTelefono(usuario.getTelefono());
        nuevoUsuario.setFechaRegistro(LocalDate.now());
        usuarioRepository.save(nuevoUsuario);
        return new UsuarioDTO(nuevoUsuario.getNombre(),nuevoUsuario.getApellido(),nuevoUsuario.getEmail(), nuevoUsuario.getTelefono(), nuevoUsuario.getDireccion(), nuevoUsuario.getFechaRegistro());
    }

    public List<UsuarioDTO> getAll() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<UsuarioDTO> usuarioDTOS = new ArrayList<>();
        for (Usuario usuario : usuarios) {

        }
    }






}
