
package com.prueba.spring.catalogo_videojuegos_api.services;

import com.prueba.spring.catalogo_videojuegos_api.models.Genero;
import com.prueba.spring.catalogo_videojuegos_api.repositories.GeneroRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GeneroService {
    private final GeneroRepository generoRepository;
    public GeneroService(GeneroRepository generoRepository) {
        this.generoRepository = generoRepository;
    }

    public List<Genero> listarTodos() {
        return generoRepository.findAll();
    }

    public Optional<Genero> buscarPorId(Integer id) {
        return generoRepository.findById(id);
    }

    public Genero guardar(Genero genero) {
        return generoRepository.save(genero);
    }
    public Genero actualizar(Integer id, Genero genero) {
        Genero generoExistente = generoRepository.findById(id).orElseThrow(() -> new java.util.NoSuchElementException("Genero no encontrado con ID: " + id));
        generoExistente.setNombre(genero.getNombre());
        generoExistente.setDescripcion(genero.getDescripcion());

        return generoRepository.save(generoExistente);
    }

    public void eliminar(Integer id) {
        Genero generoExistente = generoRepository.findById(id).orElseThrow(() -> new java.util.NoSuchElementException("Genero no encontrado con ID: " + id));
        generoRepository.delete(generoExistente);
    }
}
