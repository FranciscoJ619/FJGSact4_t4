package com.prueba.spring.catalogo_videojuegos_api.security;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Desactivar CSRF
            .csrf(csrf -> csrf.disable())
            // Desactivar autenticacion mediante formularios
            .formLogin(form -> form.disable())
            // Desactivar autenticacion HTTP Basic
            .httpBasic(basic -> basic.disable())
            // No utilizar sesiones de servidor
            .sessionManagement(session ->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // Definir las rutas publicas y protegidas
            .authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.POST,"/api/auth/register","/api/auth/login").permitAll().anyRequest().authenticated())
            // Llamar al filtro JWT
            .addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    // Evitar que se registre el filtro dos veces
    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter>jwtFilterRegistration(JwtAuthenticationFilter filter) {
        FilterRegistrationBean<JwtAuthenticationFilter> registration =new FilterRegistrationBean<>(filter);
        registration.setEnabled(false);

        return registration;
    }
}