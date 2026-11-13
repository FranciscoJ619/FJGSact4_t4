
package com.prueba.spring.catalogo_videojuegos_api.controllers;

import com.prueba.spring.catalogo_videojuegos_api.dto.videojuego.VideojuegoRequestDTO;
import com.prueba.spring.catalogo_videojuegos_api.dto.videojuego.VideojuegoResponseDTO;
import com.prueba.spring.catalogo_videojuegos_api.models.Genero;
import com.prueba.spring.catalogo_videojuegos_api.models.Videojuego;
import com.prueba.spring.catalogo_videojuegos_api.services.GeneroService;
import com.prueba.spring.catalogo_videojuegos_api.services.VideojuegoService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/videojuegos")
public class VideojuegoController {
    private final VideojuegoService videojuegoService;
    private final GeneroService generoService;
    public VideojuegoController(VideojuegoService videojuegoService,GeneroService generoService) {
        this.videojuegoService = videojuegoService;
        this.generoService = generoService;
    }

    @GetMapping
    public ResponseEntity<Page<VideojuegoResponseDTO>> listar(@PageableDefault(size = 10, sort = "id") Pageable pageable) {
        Page<VideojuegoResponseDTO> respuesta =videojuegoService.listar(pageable).map(this::convertirAResponseDTO);

        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideojuegoResponseDTO> buscarPorId(@PathVariable Integer id) {
        Videojuego videojuego = videojuegoService.buscarPorId(id).orElseThrow(() -> new NoSuchElementException("Videojuego no encontrado"));

        return ResponseEntity.ok(convertirAResponseDTO(videojuego));
    }

    @PostMapping
    public ResponseEntity<VideojuegoResponseDTO> crear(@Valid @RequestBody VideojuegoRequestDTO datos) {
        Videojuego videojuego = convertirAEntidad(datos);
        Videojuego guardado = videojuegoService.guardar(videojuego);

        return ResponseEntity.status(HttpStatus.CREATED).body(convertirAResponseDTO(guardado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VideojuegoResponseDTO> actualizar(@PathVariable Integer id,@Valid @RequestBody VideojuegoRequestDTO datos) {
        Videojuego videojuego = videojuegoService.buscarPorId(id).orElseThrow(() -> new NoSuchElementException("Videojuego no encontrado"));
        videojuego.setNombre(datos.getNombre());
        videojuego.setDesarrollador(datos.getDesarrollador());
        videojuego.setGenero(buscarGenero(datos.getGeneroId()));

        Videojuego actualizado = videojuegoService.guardar(videojuego);

        return ResponseEntity.ok(convertirAResponseDTO(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        Videojuego videojuego = videojuegoService.buscarPorId(id).orElseThrow(() -> new NoSuchElementException("Videojuego no encontrado"));
        videojuegoService.eliminar(videojuego.getId());

        return ResponseEntity.noContent().build();
    }

    private Videojuego convertirAEntidad(VideojuegoRequestDTO datos) {
        Videojuego videojuego = new Videojuego();
        videojuego.setNombre(datos.getNombre());
        videojuego.setDesarrollador(datos.getDesarrollador());
        videojuego.setGenero(buscarGenero(datos.getGeneroId()));

        return videojuego;
    }

    private Genero buscarGenero(Integer generoId) {
        return generoService.buscarPorId(generoId).orElseThrow(() -> new NoSuchElementException("Genero no encontrado"));
    }

    private VideojuegoResponseDTO convertirAResponseDTO(Videojuego videojuego) {
        return new VideojuegoResponseDTO(videojuego.getId(),videojuego.getNombre(),videojuego.getDesarrollador(),videojuego.getGenero().getId(),
        videojuego.getGenero().getNombre());
    }
}
