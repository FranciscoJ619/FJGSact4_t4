package com.prueba.spring.catalogo_videojuegos_api.controllers;

import com.prueba.spring.catalogo_videojuegos_api.dto.genero.GeneroRequestDTO;
import com.prueba.spring.catalogo_videojuegos_api.dto.genero.GeneroResponseDTO;
import com.prueba.spring.catalogo_videojuegos_api.models.Genero;
import com.prueba.spring.catalogo_videojuegos_api.services.GeneroService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/generos")
public class GeneroController {
    private final GeneroService generoService;
    public GeneroController(GeneroService generoService) {
        this.generoService = generoService;
    }

    @GetMapping
    public ResponseEntity<List<GeneroResponseDTO>> listar() {
        List<GeneroResponseDTO> respuesta = generoService.listarTodos().stream().map(this::convertirAResponseDTO).toList();

        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneroResponseDTO> buscarPorId(@PathVariable Integer id) {
        Genero genero = generoService.buscarPorId(id).orElseThrow(() -> new NoSuchElementException("Genero no encontrado"));

        return ResponseEntity.ok(convertirAResponseDTO(genero));
    }

    @PostMapping
    public ResponseEntity<GeneroResponseDTO> crear(@Valid @RequestBody GeneroRequestDTO datos) {
        Genero genero = convertirAEntidad(datos);
        Genero guardado = generoService.guardar(genero);

        return ResponseEntity.status(HttpStatus.CREATED).body(convertirAResponseDTO(guardado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GeneroResponseDTO> actualizar(@PathVariable Integer id,@Valid @RequestBody GeneroRequestDTO datos) {
        Genero genero = convertirAEntidad(datos);
        Genero actualizado = generoService.actualizar(id, genero);

        return ResponseEntity.ok(convertirAResponseDTO(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        generoService.eliminar(id);

        return ResponseEntity.noContent().build();
    }

    private Genero convertirAEntidad(GeneroRequestDTO datos) {
        Genero genero = new Genero();
        genero.setNombre(datos.getNombre());
        genero.setDescripcion(datos.getDescripcion());

        return genero;
    }

    private GeneroResponseDTO convertirAResponseDTO(Genero genero) {
        return new GeneroResponseDTO(genero.getId(),genero.getNombre(),genero.getDescripcion());
    }
}
