package com.tienda.microusuario.service;

import com.tienda.microusuario.dto.UsuarioDTO;
import com.tienda.microusuario.entity.Usuario;
import com.tienda.microusuario.mapper.UsuarioMapper;
import com.tienda.microusuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    private UsuarioRepository usuarioRepository;
    private UsuarioMapper usuarioMapper;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
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

    @Transactional(readOnly = true)
    public List<UsuarioDTO> getAll() {
         List<Usuario>usuarios = usuarioRepository.findAll();
         return usuarios.stream().map(usuarioMapper::toUsuarioDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UsuarioDTO findById(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        return usuarioMapper.toUsuarioDTO(usuario);
    }

    @Transactional
    public void delete(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));
        usuarioRepository.delete(usuario);
    }

    @Transactional
    public UsuarioDTO updateUsuario(Long id, UsuarioDTO usuario) {
        Usuario usuarioBuscado = usuarioRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));
        usuarioBuscado.setNombre(usuario.getNombre());
        usuarioBuscado.setApellido(usuario.getApellido());
        usuarioBuscado.setEmail(usuario.getEmail());
        usuarioBuscado.setTelefono(usuario.getTelefono());
        usuarioBuscado.setDireccion(usuario.getDireccion());
        usuarioBuscado.setFechaRegistro(usuario.getFechaRegistro());
        usuarioRepository.save(usuarioBuscado);
        return usuarioMapper.toUsuarioDTO(usuarioBuscado);
    }






}
