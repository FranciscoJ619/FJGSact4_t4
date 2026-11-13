package com.prueba.spring.catalogo_videojuegos_api.services;

import com.prueba.spring.catalogo_videojuegos_api.models.Genero;
import com.prueba.spring.catalogo_videojuegos_api.repositories.GeneroRepository;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GeneroService {

    private final GeneroRepository generoRepository;

    public GeneroService(GeneroRepository generoRepository) {
        this.generoRepository = generoRepository;
    }

    public Optional<Genero> buscarPorId(Integer id) {
        return generoRepository.findById(id);
    }
}