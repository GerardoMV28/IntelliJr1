package com.proyecto.usuarios.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Clase abstracta base para solicitudes que utilizan la llave compuesta (nombre + correo)
 * como criterio de identificación del usuario.
 * Aplica principios de POO: Abstracción, Herencia y Polimorfismo.
 */
public abstract class UsuarioIdentificadorRequest {

    @Schema(description = "Nombre completo del usuario para identificarlo", example = "Juan Pérez", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El nombre es obligatorio y no puede contener solo espacios.")
    private String nombre;

    @Schema(description = "Correo electrónico del usuario para identificarlo", example = "juan.perez@correo.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El correo electrónico es obligatorio.")
    @Email(message = "El correo electrónico no tiene un formato válido.")
    private String correo;

    public UsuarioIdentificadorRequest() {
    }

    public UsuarioIdentificadorRequest(String nombre, String correo) {
        this.nombre = nombre;
        this.correo = correo;
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
