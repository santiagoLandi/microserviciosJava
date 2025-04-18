package com.tienda.usuariosservice.repository;

import com.tienda.usuariosservice.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query ("SELECT u "+
            "FROM Usuario u "+
            "WHERE u.nombre = :nombre")
    List<Usuario>buscarPorNombre(@Param("nombre") String nombre);

    @Query ("SELECT u "+
            "FROM Usuario u "+
            "WHERE u.apellido = :apellido")
    List<Usuario>buscarPorApellido(@Param("apellido") String apellido);

    @Query ("SELECT u "+
            "FROM Usuario u "+
            "WHERE u.email = :email")
    List<Usuario>buscarPorEmail(@Param("email") String email);

}
