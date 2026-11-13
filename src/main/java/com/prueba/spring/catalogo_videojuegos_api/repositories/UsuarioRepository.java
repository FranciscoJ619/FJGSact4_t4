package com.prueba.spring.catalogo_videojuegos_api.repositories;

import com.prueba.spring.catalogo_videojuegos_api.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByCorreo(String correo);

    boolean existsByCorreo(String correo);
}