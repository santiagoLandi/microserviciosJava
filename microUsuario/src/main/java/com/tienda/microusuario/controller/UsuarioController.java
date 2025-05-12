package com.tienda.microusuario.controller;

import com.tienda.microusuario.dto.UsuarioDTO;
import com.tienda.microusuario.entity.Usuario;
import com.tienda.microusuario.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> saveUsuario(@Valid @RequestBody UsuarioDTO usuario) {
        UsuarioDTO nuevoUsuario = usuarioService.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllUsuarios() {
        List<UsuarioDTO> usuarios= usuarioService.getAll();
        if(usuarios.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUsuarioById(@PathVariable Long id) {
        UsuarioDTO usuarioBuscado = usuarioService.findById(id);
        if(usuarioBuscado == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(usuarioBuscado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable Long id) {
        usuarioService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuario) {
        UsuarioDTO usuarioActualizado =usuarioService.updateUsuario(id,usuario);
        return ResponseEntity.ok(usuarioActualizado);
    }
}
