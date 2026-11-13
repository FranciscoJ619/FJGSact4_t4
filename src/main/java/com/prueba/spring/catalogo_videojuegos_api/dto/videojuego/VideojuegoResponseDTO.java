package com.prueba.spring.catalogo_videojuegos_api.dto.videojuego;

public class VideojuegoResponseDTO {

    private Integer id;
    private String nombre;
    private String desarrollador;
    private Integer generoId;
    private String generoNombre;

    public VideojuegoResponseDTO() {
    }

    public VideojuegoResponseDTO(Integer id, String nombre, String desarrollador, Integer generoId, String generoNombre) {
        this.id = id;
        this.nombre = nombre;
        this.desarrollador = desarrollador;
        this.generoId = generoId;
        this.generoNombre = generoNombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDesarrollador() {
        return desarrollador;
    }

    public void setDesarrollador(String desarrollador) {
        this.desarrollador = desarrollador;
    }

    public Integer getGeneroId() {
        return generoId;
    }

    public void setGeneroId(Integer generoId) {
        this.generoId = generoId;
    }

    public String getGeneroNombre() {
        return generoNombre;
    }

    public void setGeneroNombre(String generoNombre) {
        this.generoNombre = generoNombre;
    }
}