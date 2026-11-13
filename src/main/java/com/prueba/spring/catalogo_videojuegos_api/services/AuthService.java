package com.prueba.spring.catalogo_videojuegos_api.services;

import com.prueba.spring.catalogo_videojuegos_api.dto.auth.RegisterRequestDTO;
import com.prueba.spring.catalogo_videojuegos_api.models.Usuario;
import com.prueba.spring.catalogo_videojuegos_api.repositories.UsuarioRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario registrar(RegisterRequestDTO datos) {
        if (usuarioRepository.existsByCorreo(datos.getCorreo())) {
            throw new IllegalArgumentException("El correo ya esta registrado");
        }

        Usuario usuario = new Usuario();

        usuario.setNombre(datos.getNombre());
        usuario.setCorreo(datos.getCorreo());

        usuario.setPassword(passwordEncoder.encode(datos.getPassword()));

        return usuarioRepository.save(usuario);
    }
}