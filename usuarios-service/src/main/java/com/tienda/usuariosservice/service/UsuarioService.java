package com.tienda.usuariosservice.service;

import com.tienda.usuariosservice.dto.UsuarioDTO;
import com.tienda.usuariosservice.entity.Usuario;
import com.tienda.usuariosservice.repository.UsuarioRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private UsuarioRepository repository;


    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public UsuarioDTO saveUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setTelefono(usuarioDTO.getTelefono());
        usuario.setDireccion(usuarioDTO.getDireccion());
        usuario.setFechaRegistro(LocalDate.now());
        repository.save(usuario);
        return convertToDTO(usuario);
    }

    private UsuarioDTO convertToDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setApellido(usuario.getApellido());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setTelefono(usuario.getTelefono());
        usuarioDTO.setDireccion(usuario.getDireccion());
        usuarioDTO.setFechaRegistro(usuario.getFechaRegistro());
        return usuarioDTO;
    }
    @Transactional(readOnly = true)
    public List<UsuarioDTO> getAllUsuarios() {
        List<Usuario> usuarios = repository.findAll();
        List<UsuarioDTO> usuariosDTO = new ArrayList<>();
        for(Usuario usuario : usuarios) {
            usuariosDTO.add(convertToDTO(usuario));
        }
        return usuariosDTO;
    }
    @Transactional
    public UsuarioDTO findById(Long id) {
        Usuario usuario = repository.findById(id).orElseThrow(() -> new RuntimeException("No existe el usuario con el id: " + id));
        return convertToDTO(usuario);
    }
    @Transactional(readOnly = true)
    public List<UsuarioDTO>getUsuariosPorNombre(String nombre) {
        List<Usuario>usuariosFiltrados = repository.buscarPorNombre(nombre);
        return usuariosFiltrados.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<UsuarioDTO> getUsuariosPorApellido(String apellido) {
        List<Usuario>usuariosFiltrados = repository.buscarPorApellido(apellido);
        return usuariosFiltrados.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<UsuarioDTO> getUsuariosPorEmail(String email) {
        List<Usuario>usuariosFiltrados = repository.buscarPorEmail(email);
        return usuariosFiltrados.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteUsuario(Long id) {
        if(!repository.existsById(id)) {
            throw new RuntimeException("No existe el usuario con el id: " + id);
        }
        repository.deleteById(id);
    }

    @Transactional
    public UsuarioDTO actualizarUsuario(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuario = repository.findById(id).orElseThrow(() -> new RuntimeException("No existe el usuario con el id: " + id));
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setTelefono(usuarioDTO.getTelefono());
        usuario.setDireccion(usuarioDTO.getDireccion());
        usuario.setFechaRegistro(usuarioDTO.getFechaRegistro());
        repository.save(usuario);
        return convertToDTO(usuario);
    }



}
