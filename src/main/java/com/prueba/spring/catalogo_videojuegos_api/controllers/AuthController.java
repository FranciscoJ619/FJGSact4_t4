
package com.prueba.spring.catalogo_videojuegos_api.controllers;

import com.prueba.spring.catalogo_videojuegos_api.dto.auth.AuthResponseDTO;
import com.prueba.spring.catalogo_videojuegos_api.dto.auth.LoginRequestDTO;
import com.prueba.spring.catalogo_videojuegos_api.dto.auth.RegisterRequestDTO;
import com.prueba.spring.catalogo_videojuegos_api.services.AuthService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> registrar(@Valid @RequestBody RegisterRequestDTO datos) {
        AuthResponseDTO respuesta = authService.registrar(datos);

        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO datos) {
        AuthResponseDTO respuesta = authService.login(datos);

        return ResponseEntity.ok(respuesta);
    }
}
