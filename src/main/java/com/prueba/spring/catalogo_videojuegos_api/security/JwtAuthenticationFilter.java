package com.prueba.spring.catalogo_videojuegos_api.security;

import com.prueba.spring.catalogo_videojuegos_api.models.Usuario;
import com.prueba.spring.catalogo_videojuegos_api.repositories.UsuarioRepository;

import io.jsonwebtoken.JwtException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;

    public JwtAuthenticationFilter(JwtService jwtService, UsuarioRepository usuarioRepository) {
        this.jwtService = jwtService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authHeader.substring(7);

        try {
            String correo = jwtService.obtenerCorreo(token);
            if (correo != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                Optional<Usuario> usuarioEncontrado = usuarioRepository.findByCorreo(correo);
                if (usuarioEncontrado.isPresent() && jwtService.validarToken(token, correo)) {
                    UsernamePasswordAuthenticationToken autenticacion =new UsernamePasswordAuthenticationToken(correo,null,List.of());
                    autenticacion.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(autenticacion);
                }
            }

        } catch (JwtException | IllegalArgumentException e) {
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request, response);
    }
}