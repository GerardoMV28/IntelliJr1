package com.proyecto.usuarios.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * DTO para la creación de un nuevo usuario en la base de datos.
 * Aplica todas las restricciones de Bean Validation exigidas en la especificación.
 */
@Schema(description = "Datos para el registro de un nuevo usuario")
public class UsuarioCreateRequest {

    @Schema(description = "Nombre completo del usuario", example = "Juan Pérez", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El nombre es obligatorio y no puede contener solo espacios.")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres.")
    private String nombre;

    @Schema(description = "Correo electrónico único del usuario", example = "juan.perez@correo.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El correo electrónico es obligatorio.")
    @Email(message = "El correo electrónico no tiene un formato válido.")
    @Size(max = 150, message = "El correo electrónico no debe superar los 150 caracteres.")
    private String correo;

    @Schema(description = "Teléfono de 10 dígitos numéricos", example = "4771234567", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El teléfono es obligatorio.")
    @Pattern(regexp = "^[0-9]{10}$", message = "El teléfono debe contener exactamente 10 dígitos.")
    private String telefono;

    public UsuarioCreateRequest() {
    }

    public UsuarioCreateRequest(String nombre, String correo, String telefono) {
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
