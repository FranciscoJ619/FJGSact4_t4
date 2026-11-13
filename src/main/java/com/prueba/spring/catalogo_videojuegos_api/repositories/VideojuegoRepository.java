package com.prueba.spring.catalogo_videojuegos_api.repositories;

import com.prueba.spring.catalogo_videojuegos_api.models.Videojuego;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideojuegoRepository extends JpaRepository<Videojuego, Integer> {

}