package com.tienda.usuariosservice.controller;

import com.tienda.usuariosservice.dto.UsuarioDTO;
import com.tienda.usuariosservice.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@Validated
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UsuarioDTO dto) {
        try{
            service.saveUsuario(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado con exito");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllUsuarios() {
        try{
            List<UsuarioDTO>usuarios = service.getAllUsuarios();
            if(usuarios.isEmpty()){
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(usuarios);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUsuarioById(@PathVariable("id") Long id) {
        try{
            UsuarioDTO buscado = service.findById(id);
            return(buscado !=null ? ResponseEntity.ok(buscado) : ResponseEntity.notFound().build());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllUsuariosPorFiltro(@RequestParam(value = "filter", required = false) String filtro, @RequestParam(value = "value", required = false) String valor) {
        try{
            if(filtro != null && valor !=null && !valor.isEmpty()) {
                return switch (filtro.toLowerCase()) {
                    case "nombre" -> {
                        List<UsuarioDTO> usuariosPorNombre = service.getUsuariosPorNombre(valor);
                        yield usuariosPorNombre.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(usuariosPorNombre);
                    }
                    case "email" -> {
                        List<UsuarioDTO> usuariosPorEmail = service.getUsuariosPorEmail(valor);
                        yield usuariosPorEmail.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(usuariosPorEmail);
                    }
                    case "apellido" -> {
                        List<UsuarioDTO> usuariosPorApellido = service.getUsuariosPorApellido(valor);
                        yield usuariosPorApellido.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(usuariosPorApellido);
                    }
                    default -> ResponseEntity.badRequest().body("Filtro no válido.");
                };
            }else{
                return ResponseEntity.badRequest().build();
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable("id") Long id) {
        try{
            if(id != null){
                service.deleteUsuario(id);
                return ResponseEntity.ok().body("Usuario eliminado.");
            }else{
                return ResponseEntity.badRequest().body("Usuario no encontrado.");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<?>actualizarUsuario(@PathVariable("id") Long id,@RequestBody UsuarioDTO usuarioActualizado) {
        try {
            service.actualizarUsuario(id,usuarioActualizado);
            return ResponseEntity.ok().body("Usuario actualizado.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error al actualizar el usuario: " + e.getMessage());
        }
    }
}
