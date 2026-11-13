package com.prueba.spring.catalogo_videojuegos_api.repositories;

import com.prueba.spring.catalogo_videojuegos_api.models.Genero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneroRepository extends JpaRepository<Genero, Integer> {

}