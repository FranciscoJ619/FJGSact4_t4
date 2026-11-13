package com.prueba.spring.catalogo_videojuegos_api.dto.auth;

public class AuthResponseDTO {

    private String token;
    private String tipo;
    private String nombre;
    private String correo;

    public AuthResponseDTO() {
    }

    public AuthResponseDTO(String token, String tipo, String nombre, String correo) {
        this.token = token;
        this.tipo = tipo;
        this.nombre = nombre;
        this.correo = correo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}