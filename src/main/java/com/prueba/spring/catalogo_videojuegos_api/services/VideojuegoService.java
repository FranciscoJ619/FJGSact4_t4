package com.prueba.spring.catalogo_videojuegos_api.services;

import com.prueba.spring.catalogo_videojuegos_api.models.Videojuego;
import com.prueba.spring.catalogo_videojuegos_api.repositories.VideojuegoRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VideojuegoService {

    private final VideojuegoRepository videojuegoRepository;

    public VideojuegoService(VideojuegoRepository videojuegoRepository) {
        this.videojuegoRepository = videojuegoRepository;
    }

    // Obtener todos los videojuegos con paginacion
    public Page<Videojuego> listar(Pageable pageable) {
        return videojuegoRepository.findAll(pageable);
    }

    // Buscar un videojuego por su ID
    public Optional<Videojuego> buscarPorId(Integer id) {
        return videojuegoRepository.findById(id);
    }

    // Registrar o actualizar un videojuego
    public Videojuego guardar(Videojuego videojuego) {
        return videojuegoRepository.save(videojuego);
    }

    // Eliminar un videojuego por su ID
    public void eliminar(Integer id) {
        videojuegoRepository.deleteById(id);
    }
}