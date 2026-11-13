
package com.prueba.spring.catalogo_videojuegos_api.services;

import com.prueba.spring.catalogo_videojuegos_api.dto.auth.AuthResponseDTO;
import com.prueba.spring.catalogo_videojuegos_api.dto.auth.LoginRequestDTO;
import com.prueba.spring.catalogo_videojuegos_api.dto.auth.RegisterRequestDTO;
import com.prueba.spring.catalogo_videojuegos_api.models.Usuario;
import com.prueba.spring.catalogo_videojuegos_api.repositories.UsuarioRepository;
import com.prueba.spring.catalogo_videojuegos_api.security.JwtService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UsuarioRepository usuarioRepository,PasswordEncoder passwordEncoder,JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponseDTO registrar(RegisterRequestDTO datos) {
        if (usuarioRepository.existsByCorreo(datos.getCorreo())) {
            throw new IllegalArgumentException("El correo ya esta registrado");
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(datos.getNombre());
        usuario.setCorreo(datos.getCorreo());
        usuario.setPassword(passwordEncoder.encode(datos.getPassword()));

        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        String token = jwtService.generarToken(usuarioGuardado.getCorreo());

        return new AuthResponseDTO(token,"Bearer",usuarioGuardado.getNombre(),usuarioGuardado.getCorreo());
    }

    public AuthResponseDTO login(LoginRequestDTO datos) {
        Usuario usuario = usuarioRepository.findByCorreo(datos.getCorreo()).orElseThrow(() -> new IllegalArgumentException("Credenciales incorrectas"));
        if (!passwordEncoder.matches(datos.getPassword(), usuario.getPassword())) {
            throw new IllegalArgumentException("Credenciales incorrectas");
        }
        String token = jwtService.generarToken(usuario.getCorreo());

        return new AuthResponseDTO(token,"Bearer",usuario.getNombre(),usuario.getCorreo());
    }
}
