# CRUD con Springboot, SpringSecurity y validacion JWT

## Portada

**Nombre:** Francisco Javier González Santiago

### Descripción

Este proyecto consiste en una aplicacion web que permite gestionar un catalogo de videojuegos usando operaciones CRUD (crear,
leer, actualizar y borrar)

Utiliza `MySQL` como Manejador de Base de Datos donde existen 2 tablas: Videojuego y genero, con sus propios atributos y en
especial la tabla videojuego que cuenta con la clave foranea `id_genero`. Pero no posee una funcion de frontend

Se utiliza el framwork `Spring Security` para el control de acceso a nuestra aplicacion mediante proteccion de rutas y recursos
y para esto utilizamos el estandar `JWT (JSON Web Token)` para generar tokens de exclusivos para la conexion a nuestra API

---

## Documentación

### Clases(Modelos)

Aqui se muestran las clases utilizadas dentro de MySQL

La clase `Genero`, la cual consta de atributos como id, nombre y descripcion y contiene el elemento:

```java
@OneToMany(mappedBy = "genero")
```
El cual basicamente explica que un genero puede estar vinculado a varios videojuegos

![Clase Genero](img/captura1.png)

La clase `Videojuego`, la cual consta de atributos como id, nombre y desarrollador pero contiene el elemento:

```java
@ManyToOne
@JoinColumn(name = "genero_id")
```
Lo cual nos muestra que puede tener una clave foranea

![Clase Videojuego](img/captura2.png)

La clase `Usuario`, la cual consta de atributos como id, nombre, correo y password, es la clase principal que
utilizaremos para crear llaves JWT para dar claves de acceso a los usarios

![Clase Usuario](img/captura3.png)

---

### Clases(Repositorios)

Son clases que se encargan de la comunicación con la base de datos mediante Spring Data JPA. Permiten realizar 
operaciones como guardar, consultar, actualizar y eliminar registros sin necesidad de escribir manualmente 
todas las consultas SQL.

```java
public interface GeneroRepository extends JpaRepository<Genero, Integer>{}
```

![Repositorio Genero](img/captura4.png)

![Repositorio Videojuego](img/captura5.png)

![Repositorio Usuario](img/captura6.png)

---

### Clases(Servicios)

Las clases de Servicios se encargan de procesar las operaciones solicitadas, coordinar el uso de los 
repositorios y aplicar las validaciones necesarias antes de modificar los datos.

```java
public VideojuegoService(VideojuegoRepository videojuegoRepository, GeneroRepository generoRepository)
```

![Servicio Genero](img/captura7.png)

![Servicio Videojuego](img/captura8.png)

Dentro de la clase `AuthService`, es donde se manejan los servicios de autenticacion de Usuarios, tanto el registro como el logeo

![Servicio Auth](img/captura9.png)

---

### DTO

Los DTO son objetos utilizados para transferir información entre el cliente y el servidor. Permiten definir los datos que se reciben y envían mediante los endpoints. Para cada opcion de endpoint se tiene un DTO sobre `Peticion(Request)` y `Respuesta(Response)`

![DTO Peticion Genero](img/captura10.png)

![DTO Respuesta Genero](img/captura11.png)

![DTO Peticion Videojuego](img/captura12.png)

![DTO Respuesta Videojuego](img/captura13.png)

![DTO Peticion Login](img/captura14.png)

![DTO Respuesta Autenticacion](img/captura15.png)

---

### Controladores

Los controladores son componentes encargados de gestionar las peticiones HTTP que recibe la API REST. Definen los endpoints, reciben los datos enviados por el cliente y se comunican con la capa de servicios

Utilizan:
```java
@RequestMapping("/api/auth")
```
Para definir la ruta que se debe seguir para obtener las respuestas htpp necesarias

![Controlador Genero](img/captura16.png)

![Controlador Videojuego](img/captura17.png)

![Controlador Autenticacion](img/captura18.png)

---

### Security

En esta carpeta se maneja todo lo necesario para el estandar JWT y manejar todas sus configuraciones y necesidades para asignar correctamente los tokens de autenticacion

Mediante este metodo:
```java
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
```
Se logra ajustar la necesidad de un token JWT

Y mediante este metodo:
```java
 @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
```
Se logra encriptar estos tokens JWT

Y por ultimo tenemos un filtro para lograr la autenticacion interna de nuestras claves JWT


![Controlador Genero](img/captura19.png)

![Controlador Videojuego](img/captura20.png)

![Controlador Autenticacion](img/captura21.png)

---


### Pruebas dentro de Bruno

![Peticion POST](img/captura22.png)

![Peticion GET](img/captura23.png)

![Peticion POST login](img/captura24.png)

![Peticion PUT](img/captura25.png)

![Peticion DELETE](img/captura26.png)

![Peticion GET comprobatoria](img/captura27.png)

---

Proyecto realizado para la materia de Programación Web.